package com.yundao.core.solr;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 产品实体类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class Product {

	@Field("id")
	private String id;

	@Field("title")
	private List<String> title;

	@Field("subject")
	private String subject;

	@Field("description")
	private String description;

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("id=").append(id).append(",");
		result.append("title=").append(title).append(",");
		result.append("subject=").append(subject).append(",");
		result.append("description=").append(description).append(",");
		return result.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getTitle() {
		return title;
	}

	public void setTitle(List<String> title) {
		this.title = title;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}