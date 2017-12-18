package com.yundao.core.dto.activiti;

import java.io.Serializable;

/**
 * 工作流任务dto
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class TaskDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 任务id
	 */
	private String id;

	/**
	 * 名字
	 */
	private String name;

	/**
	 * 认领人
	 */
	private String assignee;

	/**
	 * 拥有人
	 */
	private String owner;

	/**
	 * 流程实例id
	 */
	private String processInstanceId;

	/**
	 * 流程定义id
	 */
	private String processDefinitionId;

	/**
	 * 流程实例key值
	 */
	private String businessKey;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

}
