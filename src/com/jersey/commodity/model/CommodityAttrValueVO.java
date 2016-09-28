package com.jersey.commodity.model;

import com.jersey.tools.JerseyEnum.CommodityAttrAuthority;

public class CommodityAttrValueVO {
	
	private String commodityAttr;
	private String commodityAttrValue;
	private CommodityAttrAuthority commodityAttrAuthority;
	
	public String getCommodityAttr() {
		return commodityAttr;
	}
	public void setCommodityAttr(String commodityAttr) {
		this.commodityAttr = commodityAttr;
	}
	public String getCommodityAttrValue() {
		return commodityAttrValue;
	}
	public void setCommodityAttrValue(String commodityAttrValue) {
		this.commodityAttrValue = commodityAttrValue;
	}
	public CommodityAttrAuthority getCommodityAttrAuthority() {
		return commodityAttrAuthority;
	}
	public void setCommodityAttrAuthority(CommodityAttrAuthority commodityAttrAuthority) {
		this.commodityAttrAuthority = commodityAttrAuthority;
	}
	
	

}
