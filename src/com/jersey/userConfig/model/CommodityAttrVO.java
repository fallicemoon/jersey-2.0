package com.jersey.userConfig.model;

import java.util.Set;

import com.jersey.commodity.model.CommodityAttrMappingVO;
import com.jersey.tools.AbstractVo;
import com.jersey.tools.JerseyEnum.CommodityAttrAuthority;

public class CommodityAttrVO extends AbstractVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7656268428089108516L;

	private Integer commodityAttrId;
	private CommodityTypeVO commodityTypeVO;
	private String commodityAttr;
	private Set<CommodityAttrMappingVO> commodityAttrMappings;
	private CommodityAttrAuthority commodityAttrAuthority;
	
	public Integer getCommodityAttrId() {
		return commodityAttrId;
	}
	public void setCommodityAttrId(Integer commodityAttrId) {
		this.commodityAttrId = commodityAttrId;
	}
	public CommodityTypeVO getCommodityTypeVO() {
		return commodityTypeVO;
	}
	public void setCommodityTypeVO(CommodityTypeVO commodityTypeVO) {
		this.commodityTypeVO = commodityTypeVO;
	}
	public String getCommodityAttr() {
		return commodityAttr;
	}
	public void setCommodityAttr(String commodityAttr) {
		this.commodityAttr = commodityAttr;
	}
	public Set<CommodityAttrMappingVO> getCommodityAttrMappings() {
		return commodityAttrMappings;
	}
	public void setCommodityAttrMappings(Set<CommodityAttrMappingVO> commodityAttrMappings) {
		this.commodityAttrMappings = commodityAttrMappings;
	}
	public CommodityAttrAuthority getCommodityAttrAuthority() {
		return commodityAttrAuthority;
	}
	public void setCommodityAttrAuthority(CommodityAttrAuthority commodityAttrAuthority) {
		this.commodityAttrAuthority = commodityAttrAuthority;
	}
	
	@Override
	public String toString() {
		return "CommodityAttrVO [commodityAttrId=" + commodityAttrId + ", commodityTypeVO=" + commodityTypeVO
				+ ", commodityAttrAuthority="
				+ commodityAttrAuthority + "]";
	}
	

	
	
}
