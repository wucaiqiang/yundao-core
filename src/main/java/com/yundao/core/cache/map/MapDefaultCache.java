package com.yundao.core.cache.map;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.yundao.core.cache.list.NodeList;
import com.yundao.core.log.Log;
import com.yundao.core.utils.BooleanUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.yundao.core.cache.DefaultCache;
import com.yundao.core.cache.handler.RemoveHandler;
import com.yundao.core.cache.handler.TimeoutRemoveHandler;
import com.yundao.core.cache.list.Node;
import com.yundao.core.cache.visitor.Visitable;
import com.yundao.core.cache.visitor.Visitor;
import com.yundao.core.log.LogFactory;

/**
 * map默认缓存
 * 
 * @author wupengfei wupf86@126.com
 *
 * @param <K>
 * @param <V>
 */
public class MapDefaultCache<K, V> implements DefaultCache<K, V>, Visitable {

	private static Log log = LogFactory.getLog(MapDefaultCache.class);

	private Map<K, Node<K, V>> cache = new ConcurrentHashMap<K, Node<K, V>>();
	private NodeList<K, V> nl = new NodeList<K, V>();
	private Lock lock = new ReentrantLock();

	private AtomicLong queryCount = new AtomicLong();
	private AtomicLong hitCount = new AtomicLong();

	private String name;
	private long timeout = -1;
	private int maxSize = -1;
	private RemoveHandler<K, V> handler = null;

	private volatile long lastRemoveTime = 0;
	private volatile long lastRemoveConsumeTime = 0;
	private volatile boolean lastRemoveStatus = false;

	public MapDefaultCache() {
		init(null);
	}

	public MapDefaultCache(Map<String, Object> map) {
		init(map);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void init(Map<String, Object> map) {
		log.begin("初始化map默认缓存，map=", map);
		if (!BooleanUtils.isEmpty(map)) {
			Object timeoutMap = map.get("timeout");
			if (timeoutMap != null) {
				timeout = NumberUtils.toLong(timeoutMap.toString());
				if (timeout != -1) {
					timeout *= 1000;
				}
			}

			Object maxSizeMap = map.get("maxSize");
			if (maxSizeMap != null) {
				this.maxSize = NumberUtils.toInt(maxSizeMap.toString());
			}

			Object handlerMap = map.get("removeHandler");
			if (handlerMap != null) {
				try {
					Class clazz = ClassUtils.getClass(handlerMap.toString());
					handler = (RemoveHandler) clazz.newInstance();
				}
				catch (Exception e) {
					log.error("初始化删除缓存处理类时异常", e);
				}
			}
		}
		if (handler == null) {
			handler = new TimeoutRemoveHandler<K, V>(timeout);
		}
		log.end();
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public V get(K key) {
		queryCount.incrementAndGet();

		Node<K, V> n = cache.get(key);
		if (n != null) {
			// 是否需要删除
			if (handler.handle(n)) {
				removeNode(n);
				return null;
			}

			hitCount.incrementAndGet();
			lock.lock();
			try {
				nl.moveToTop(n);
			}
			finally {
				lock.unlock();
			}
			return n.getValue();
		}
		return null;
	}

	@Override
	public boolean containsKey(K key) {
		return cache.containsKey(key);
	}

	@Override
	public V put(K key, V value) {
		Node<K, V> n = new Node<K, V>(key, value);
		n = putNode(n);
		return n != null ? n.getValue() : null;
	}

	@Override
	public void clear() {
		this.queryCount.set(0);
		this.hitCount.set(0);

		lock.lock();
		try {
			cache.clear();
			nl.clear();
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public V remove(K key) {
		lock.lock();
		try {
			Node<K, V> n = cache.remove(key);
			if (n != null) {
				nl.remove(n);
				return n.getValue();
			}
		}
		finally {
			lock.unlock();
		}
		return null;
	}

	@Override
	public long size() {
		return cache.size();
	}

	@Override
	public Set<K> keySet() {
		return cache.keySet();
	}

	@Override
	public long getQueryCount() {
		return queryCount.get();
	}

	@Override
	public long getHitCount() {
		return hitCount.get();
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new CacheIterator();
	}

	@Override
	public int remove(RemoveHandler<K, V> h) {
		log.info("开始删除缓存");
		if (h == null) {
			h = handler;
		}

		// 删除过期数据
		lastRemoveTime = System.currentTimeMillis();
		int result = 0;
		if (timeout > 0) {
			Set<Map.Entry<K, Node<K, V>>> entrySet = cache.entrySet();
			Iterator<Map.Entry<K, Node<K, V>>> it = entrySet.iterator();
			while (it.hasNext()) {
				Map.Entry<K, Node<K, V>> entry = it.next();
				Node<K, V> node = entry.getValue();
				if (h.handle(node)) {
					removeNode(node);
					result++;
				}
			}
		}

		// 删除最近最少访问的数据
		while (maxSize > 0 && size() > maxSize) {
			Node<K, V> node = nl.getTail();
			if (node == null) {
				break;
			}
			removeNode(node);
			result++;
		}

		long removeEnd = System.currentTimeMillis();
		lastRemoveConsumeTime = removeEnd - lastRemoveTime;
		lastRemoveStatus = true;
		log.info("已删除缓存, lastRemoveTime=" + lastRemoveTime + ", lastRemoveConsumeTime=" + lastRemoveConsumeTime
				+ ", remove size=" + result);
		return result;
	}

	@Override
	public long getLastRemoveTime() {
		return lastRemoveTime;
	}

	@Override
	public long getLastRemoveConsumeTime() {
		return lastRemoveConsumeTime;
	}

	@Override
	public boolean getLastRemoveStatus() {
		return lastRemoveStatus;
	}

	@Override
	public void accept(Visitor v, String name) {
		v.visitCache(this, name);
	}

	private V removeNode(Node<K, V> n) {
		lock.lock();
		try {
			Node<K, V> old = cache.get(n.getKey());
			if (old == n) {
				cache.remove(n.getKey());
				nl.remove(n);
				return n.getValue();
			}
		}
		finally {
			lock.unlock();
		}
		return null;
	}

	private Node<K, V> putNode(Node<K, V> n) {
		lock.lock();
		try {
			Node<K, V> old = cache.put(n.getKey(), n);
			nl.add(n);
			if (old != null) {
				nl.remove(old);
			}
			return old;
		}
		finally {
			lock.unlock();
		}
	}

	private class CacheIterator implements Iterator<Map.Entry<K, V>> {

		private Node<K, V> next;

		public CacheIterator() {
			lock.lock();
			try {
				next = nl.getHead();
			}
			finally {
				lock.unlock();
			}
		}

		private class Entry<Key, Value> implements Map.Entry<Key, Value> {
			private Key k;
			private Value v;

			public Entry(Key k, Value v) {
				this.k = k;
				this.v = v;
			}

			@Override
			public Key getKey() {
				return k;
			}

			@Override
			public Value getValue() {
				return v;
			}

			@Override
			public Value setValue(Value v) {
				Value old = this.v;
				this.v = v;
				return old;
			}
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public Entry<K, V> next() {
			Node<K, V> n = nextNode();
			return new Entry<K, V>(n.getKey(), n.getValue());
		}

		@Override
		public void remove() {
			removeNode(next);
		}

		private Node<K, V> nextNode() {
			Node<K, V> n = next;
			lock.lock();
			try {
				next = next.getNext();
			}
			finally {
				lock.unlock();
			}
			return n;
		}
	}

}
