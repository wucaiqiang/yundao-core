package com.yundao.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;

/**
 * 序列化工具类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class SerializeUtils {

	private static Log log = LogFactory.getLog(SerializeUtils.class);

	/**
	 * 序列化对象
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		if (object == null) {
			return null;
		}

		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		byte[] bytes = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			bytes = baos.toByteArray();
		}
		catch (Exception e) {
			log.error("序列化时发生异常", e);
		}
		finally {
			CloseableUtils.close(oos);
			CloseableUtils.close(baos);
		}
		return bytes;
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		}
		catch (Exception e) {
			log.error("反序列化时发生异常", e);
		}
		finally {
			CloseableUtils.close(bais);
			CloseableUtils.close(ois);
		}
		return null;
	}

}