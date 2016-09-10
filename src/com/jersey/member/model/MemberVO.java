package com.jersey.member.model;

import com.jersey.tools.AbstractVo;

public class MemberVO extends AbstractVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 907864726400870241L;
	
	private String userName;
	private String password;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
