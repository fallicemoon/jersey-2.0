package com.jersey.tools;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8875731174904349257L;
	private Date createTime = new Date();
	private Date lastModifyTime = new Date();
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
	

}
