package com.jersey.systemParam.model;

import com.jersey.tools.AbstractVo;

public class SystemParamVO extends AbstractVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3052429180294747780L;
	
	private String name;
	private String value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
