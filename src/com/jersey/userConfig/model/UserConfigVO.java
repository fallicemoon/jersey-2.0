package com.jersey.userConfig.model;

import com.jersey.tools.AbstractVo;
import com.jersey.tools.JerseyEnum.Authority;

public class UserConfigVO extends AbstractVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8297622549211793706L;
	
	private String userName;
	private Authority authority;
	private Integer commodityPageSize;
	private Integer purchaseCasePageSize;
	private Integer sellCasePageSize;
	private Integer storePageSize;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Authority getAuthority() {
		return authority;
	}
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	public Integer getCommodityPageSize() {
		return commodityPageSize;
	}
	public void setCommodityPageSize(Integer commodityPageSize) {
		this.commodityPageSize = commodityPageSize;
	}
	public Integer getPurchaseCasePageSize() {
		return purchaseCasePageSize;
	}
	public void setPurchaseCasePageSize(Integer purchaseCasePageSize) {
		this.purchaseCasePageSize = purchaseCasePageSize;
	}
	public Integer getSellCasePageSize() {
		return sellCasePageSize;
	}
	public void setSellCasePageSize(Integer sellCasePageSize) {
		this.sellCasePageSize = sellCasePageSize;
	}
	public Integer getStorePageSize() {
		return storePageSize;
	}
	public void setStorePageSize(Integer storePageSize) {
		this.storePageSize = storePageSize;
	}


}
