package com.yundao.core.cache.list;

/**
 * 节点对象
 * 
 * @author wupengfei wupf86@126.com
 *
 * @param <K>
 * @param <V>
 */
public final class Node<K, V> {

	private K key;
	private V value;
	private long createTime;
	Node<K, V> next;
	Node<K, V> prev;

	public Node(K k, V v) {
		this(k, v, System.currentTimeMillis());
	}

	public Node(K k, V v, long createTime) {
		this.key = k;
		this.value = v;
		this.createTime = createTime;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.createTime = System.currentTimeMillis();
		this.value = value;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Node<K, V> getNext() {
		return next;
	}

	public Node<K, V> getPrev() {
		return prev;
	}

}
