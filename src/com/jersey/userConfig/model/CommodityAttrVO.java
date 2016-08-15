package com.jersey.userConfig.model;

import com.jersey.tools.AbstractVo;
import com.jersey.tools.JerseyEnum.CommodityAttrAuthority;

public class CommodityAttrVO extends AbstractVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7656268428089108516L;

	private Integer commodityAttrId;
	private String commodityType;
	private String commodityAttr;
	private CommodityAttrAuthority commodityAttrAuthority;
	
	public Integer getCommodityAttrId() {
		return commodityAttrId;
	}
	public void setCommodityAttrId(Integer commodityAttrId) {
		this.commodityAttrId = commodityAttrId;
	}
	public String getCommodityType() {
		return commodityType;
	}
	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}
	public String getCommodityAttr() {
		return commodityAttr;
	}
	public void setCommodityAttr(String commodityAttr) {
		this.commodityAttr = commodityAttr;
	}
	public CommodityAttrAuthority getCommodityAttrAuthority() {
		return commodityAttrAuthority;
	}
	public void setCommodityAttrAuthority(CommodityAttrAuthority commodityAttrAuthority) {
		this.commodityAttrAuthority = commodityAttrAuthority;
	}
	
	@Override
	public String toString() {
		return "CommodityAttrVO [commodityAttrId=" + commodityAttrId + ", commodityType=" + commodityType
				+ ", commodityAttr=" + commodityAttr + ", commodityAttrAuthority=" + commodityAttrAuthority + "]";
	}
	
	
}
