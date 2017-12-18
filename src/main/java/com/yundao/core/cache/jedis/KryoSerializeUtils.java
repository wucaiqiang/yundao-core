package com.yundao.core.cache.jedis;

import com.yundao.core.exception.BaseRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoSerializeUtils {

	private static final Logger logger = LoggerFactory.getLogger(KryoSerializeUtils.class);
	static{
		 
	}
	/**
	 * 序列化对象
	 * @author jon
	 * @date 2015年7月28日
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		Kryo kryo = getKryo();
		Output output = null;
		try {
			output = new Output(1, -1);
			kryo.writeClassAndObject(output, object);
			byte[] bytes = output.toBytes();
			output.flush();
			return bytes;
		} catch (Exception e) {
			logger.error("序列化失败",e);
			throw new BaseRuntimeException(e);
		}
	}

	/**
	 * 序列化对象
	 * @author jon
	 * @date 2015年7月28日
	 * @param object
	 * @return
	 */
	public static <T> byte[] serialize(Object object, Class<T> type) {
		Kryo kryo = getKryo();
		Output output = null;
		try {
			output = new Output(1,  -1);
			kryo.writeObjectOrNull(output, object, type);
			byte[] bytes = output.toBytes();
			output.flush();
			return bytes;
		} catch (Exception e) {
			logger.error("序列化失败",e);
			e.printStackTrace();
			throw new BaseRuntimeException(e);
		}
	}

	/**
	 * 反序列化
	 * @author jon
	 * @date 2015年7月28日
	 * @param bytes
	 * @return
	 */
	public static <T> T deserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		try {
			Kryo kryo = getKryo();
			Input input = new Input(bytes);
			@SuppressWarnings("unchecked")
			T t = (T) kryo.readClassAndObject(input);
			input.close();
			return t;
		} catch (Exception e) {
			logger.error("序列化失败",e);
			e.printStackTrace();
			throw new BaseRuntimeException(e);
		}
	}

	public static <T> T deserialize(byte[] bytes, Class<T> type) {
		if (bytes == null) {
			return null;
		}
		try {
			Kryo kryo = getKryo();
			Input input = new Input(bytes);
			T obj = kryo.readObject(input, type);
			input.close();
			return obj;
		} catch (Exception e) {
			logger.error("序列化失败",e);
			throw new BaseRuntimeException(e);
		}
	}

	private static Kryo getKryo(){//TODO 用池的方式
		Kryo kryo = new Kryo();
		kryo.setClassLoader(KryoSerializeUtils.class.getClassLoader());
		return kryo;
	}
}
