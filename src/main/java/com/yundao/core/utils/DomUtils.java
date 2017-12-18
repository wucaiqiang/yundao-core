package com.yundao.core.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.common.base.Strings;
import com.google.common.io.Closeables;
import com.google.common.io.Resources;

/**
 * w3c xml工具类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class DomUtils {

	/**
	 * 获取文档对象
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static Document getDocument(String path) throws Exception {
		if (Strings.isNullOrEmpty(path)) {
			return null;
		}

		InputStream is = null;
		try {
			is = new FileInputStream(path);
		}
		catch (Exception e) {
			is = Resources.getResource(path).openStream();
		}
		return getDocument(is);
	}

	/**
	 * 获取文档对象
	 * 
	 * @param is
	 * @return
	 * @throws DocumentException
	 */
	public static Document getDocument(InputStream is) throws Exception {
		return getDocument(is, true);
	}

	/**
	 * 获取文档对象，若参数isClose为true则会关闭输入流，否则不关闭
	 * 
	 * @param is
	 * @param isClose
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(InputStream is, boolean isClose) throws Exception {
		if (is == null) {
			return null;
		}

		Document doc = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(is);
		}
		finally {
			if (isClose) {
				Closeables.closeQuietly(is);
			}
		}
		return doc;
	}

	/**
	 * 根据元素名获取文档中同名的元素
	 * 
	 * @param document
	 * @param elementName
	 * @return
	 */
	public static List<Element> getElementsByName(Document document, String elementName) {
		if (document == null) {
			return null;
		}
		return getElementsByName(document.getDocumentElement(), elementName);
	}

	/**
	 * 根据元素名获取元素中同名的元素
	 * 
	 * @param element
	 * @param elementName
	 * @return
	 */
	public static List<Element> getElementsByName(Element element, String elementName) {
		if (element == null || Strings.isNullOrEmpty(elementName)) {
			return null;
		}

		List<Element> result = new ArrayList<Element>();
		NodeList children = element.getElementsByTagName(elementName);
		int length = children.getLength();
		for (int i = 0; i < length; i++) {
			Element child = (Element) children.item(i);
			result.add(child);
		}
		return result;
	}

	/**
	 * 根据元素名获取元素中同名的元素
	 * 
	 * @param element
	 * @param elementName
	 * @return
	 */
	public static Element getElementByName(Element element, String elementName) {
		List<Element> elementList = getElementsByName(element, elementName);
		if (elementList != null && elementList.size() == 1) {
			return elementList.get(0);
		}
		return null;
	}

	/**
	 * 获取给定元素的属性名的属性值
	 * 
	 * @param element
	 * @param elementName
	 * @param attributeName
	 * @return
	 */
	public static String getConfigValueByName(Element element, String elementName, String attributeName) {
		Element result = getElementByName(element, elementName);
		if (result != null) {
			return result.getAttribute(attributeName);
		}
		return null;
	}

}
