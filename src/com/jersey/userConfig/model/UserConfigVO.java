package com.jersey.userConfig.model;

import java.sql.Clob;

import com.jersey.tools.AbstractVo;

public class UserConfigVO extends AbstractVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8297622549211793706L;
	
	private Integer userConfigId;
	private Integer commodityPageSize;
	private Integer purchaseCasePageSize;
	private Integer sellCasePageSize;
	private Integer storePageSize;
	private Clob commodityAttr;
	
	public Integer getUserConfigId() {
		return userConfigId;
	}
	public void setUserConfigId(Integer userConfigId) {
		this.userConfigId = userConfigId;
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
	public Clob getCommodityAttr() {
		return commodityAttr;
	}
	public void setCommodityAttr(Clob commodityAttr) {
		this.commodityAttr = commodityAttr;
	}
	

	
	

}
