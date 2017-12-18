package com.yundao.core.utils;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;

import java.io.*;

public class ObjectAndByteUtils {
    private static Log logger=LogFactory.getLog(ObjectAndByteUtils.class);
	/**
	 * 对象转数组
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] toByteArray(Object obj) {
		if (obj == null) {
			return null;
		}
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException e) {
			logger.error("对象转换异常，异常信息为：",e);
		}
		return bytes;
	}

	/**
	 * 数组转对象
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object toObject(byte[] bytes) {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException e) {
			logger.error("对象转换异常，异常信息为：",e);
		} catch (ClassNotFoundException ex) {
			logger.error("对象转换异常，异常信息为：",ex);
		}
		return obj;
	}
}
