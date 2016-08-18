package com.jersey.commodity.model;

import java.util.Set;

import com.jersey.tools.AbstractVo;
import com.jersey.userConfig.model.CommodityAttrVO;

public class CommodityTypeVO extends AbstractVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7806107519331559770L;
	
	private Integer commodityTypeId;
	private String commodityType;
	private Set<CommodityVO> commoditys;
	private Set<CommodityAttrVO> commodityAttrs;
	
	public Integer getCommodityTypeId() {
		return commodityTypeId;
	}
	public void setCommodityTypeId(Integer commodityTypeId) {
		this.commodityTypeId = commodityTypeId;
	}
	public String getCommodityType() {
		return commodityType;
	}
	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}
	public Set<CommodityVO> getCommoditys() {
		return commoditys;
	}
	public void setCommoditys(Set<CommodityVO> commoditys) {
		this.commoditys = commoditys;
	}
	public Set<CommodityAttrVO> getCommodityAttrs() {
		return commodityAttrs;
	}
	public void setCommodityAttrs(Set<CommodityAttrVO> commodityAttrs) {
		this.commodityAttrs = commodityAttrs;
	}

	
}
