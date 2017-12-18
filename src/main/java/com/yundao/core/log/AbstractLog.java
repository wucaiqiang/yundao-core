package com.yundao.core.log;

import com.yundao.core.utils.BooleanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 抽象日志接口
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class AbstractLog implements Log {

	@Override
	public void info(Supplier<String> supplier) {
		if (this.isInfoEnabled() && supplier != null) {
			info(supplier.get());
		}
	}

	@Override
	public void begin(Object... msg) {
		try {
			if (!this.isInfoEnabled()) {
				return;
			}

			StringBuilder logs = new StringBuilder();
			logs.append("begin:").append(this.getFileInfo(3));

			int length = 0;
			if (msg != null && (length = msg.length) != 0) {
				logs.append("<ps>");
				for (int i = 0; i < length; i++) {
					logs.append("<p" + i + ">");
					String value = this.getObjectToString(msg[i]);
					if (null == value) {
						logs.append(this.getAttribute("", msg[i]));
					}
					else {
						logs.append(value);
					}
					logs.append("</p" + i + ">");
				}
				logs.append("</ps>");
			}
			this.info(logs.toString());
		}
		catch (Throwable t) {
			String error = ExceptionUtils.getStackTrace(t);
			this.error(error);
		}
	}

	@Override
	public void end() {
		if (!this.isInfoEnabled()) {
			return;
		}
		StringBuilder logs = new StringBuilder();
		logs.append("end:").append(this.getFileInfo(3));
		this.info(logs.toString());
	}

	private String getFileInfo(int stackLevel) {
		StackTraceElement stack = Thread.currentThread().getStackTrace()[stackLevel];
		return stack.getClassName() + "." + stack.getMethodName() + "." + stack.getLineNumber();
	}

	/**
	 * 转化对象为字符串
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	private String getObjectToString(Object object) throws IOException {
		String result = null;
		if (BooleanUtils.isSimpleType(object)) {
			result = object.toString();
		}
		return result;
	}

	/**
	 * 获取对象的属性值
	 * 
	 * @param name
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private String getAttribute(String name, Object object) throws Exception {
		StringBuilder result = new StringBuilder();
		if (object == null) {
			return result.append("null").toString();
		}

		if (BooleanUtils.isSimpleType(object)) {
			if (!BooleanUtils.isEmpty(name)) {
				result.append(name).append("=");
			}
			result.append(object.toString());
		}
		else if (Map.class.isAssignableFrom(object.getClass())) {
			Map<?, ?> map = (Map<?, ?>) object;
			if (BooleanUtils.isEmpty(map)) {
				return result.append("null").toString();
			}

			result.append("{");
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				result.append("[").append(this.getAttribute("key", entry.getKey())).append(",")
						.append(this.getAttribute("value", entry.getValue())).append("]");
			}
			result.append("}");
		}
		else if (Collection.class.isAssignableFrom(object.getClass())) {
			Collection<?> collection = (Collection<?>) object;
			if (BooleanUtils.isEmpty(collection)) {
				return result.append("null").toString();
			}

			result.append("[");
			boolean isDelete = false;
			for (Iterator<?> it = collection.iterator(); it.hasNext();) {
				result.append(this.getAttribute(null, it.next())).append(",");
				isDelete = true;
			}
			if (isDelete) {
				result.deleteCharAt(result.length() - 1);
			}
			result.append("]");
		}
		else if (object.getClass().isArray()) {
			if (!BooleanUtils.isEmpty(name)) {
				result.append(name).append("=");
			}
			result.append(this.getArray(object));
		}
		else {
			try {
				Class<?> clazz = object.getClass();
				result.append(clazz.getName()).append("{");

				Method[] methods = clazz.getDeclaredMethods();
				boolean isDefineToString = false;
				for (Method method : methods) {
					if (method.getName().equals("toString")) {
						isDefineToString = true;
						break;
					}
				}

				if (isDefineToString) {
					result.append("toString(").append(object.toString()).append(")");
				}
				else {
					BeanInfo info = Introspector.getBeanInfo(object.getClass());
					PropertyDescriptor[] props = info.getPropertyDescriptors();
					boolean isDelete = false;
					for (PropertyDescriptor prop : props) {
						if (prop.getReadMethod() == null || prop.getWriteMethod() == null) {
							continue;
						}
						Method readMethod = prop.getReadMethod();
						Object value = readMethod.invoke(object);
						result.append(this.getAttribute(prop.getName(), value)).append(",");
						isDelete = true;
					}
					if (isDelete) {
						result.deleteCharAt(result.length() - 1);
					}
				}
				result.append("}");
			}
			catch (Throwable t) {
				result = new StringBuilder();
				result.append("[Exception]").append(ExceptionUtils.getStackTrace(t));
			}
		}
		return result.toString();
	}

	/**
	 * 获取数组的值
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private String getArray(Object object) throws Exception {
		StringBuilder result = new StringBuilder();
		result.append("[");
		int length = Array.getLength(object);
		for (int i = 0; i < length; i++) {
			Object value = Array.get(object, i);
			boolean isArray = value.getClass().isArray();
			if (isArray) {
				result.append(this.getArray(value));
			}
			else {
				result.append(this.getAttribute(null, value));
				if (i != length - 1) {
					result.append(",");
				}
			}
		}
		result.append("]");
		return result.toString().trim();
	}
	
	@Override
	public void debug(CharSequence charSequence, Object... args) {
		if (null != charSequence && null != args) {
			debug(String.format(charSequence.toString(), args));
		}
	}

	@Override
	public void info(CharSequence charSequence, Object... args) {
		if (null != charSequence && null != args) {
			info(String.format(charSequence.toString(), args));
		}
	}

	@Override
	public void warn(CharSequence charSequence, Object... args) {
		if (null != charSequence && null != args) {
			warn(String.format(charSequence.toString(), args));
		}
	}

	@Override
	public void error(CharSequence charSequence, Object... args) {
		if (null != charSequence && null != args) {
			error(String.format(charSequence.toString(), args));
		}
	}

}
