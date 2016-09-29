package com.jersey.commodity.model;

import com.jersey.tools.AbstractVo;
import com.jersey.userConfig.model.CommodityAttrVO;

public class CommodityAttrMappingVO extends AbstractVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1103527345568677121L;
	
	private Integer commodityAttrMappingId;
	private CommodityVO commodityVO;
	private CommodityAttrVO commodityAttrVO;
	private String commodityAttrValue;
	
	public Integer getCommodityAttrMappingId() {
		return commodityAttrMappingId;
	}
	public void setCommodityAttrMappingId(Integer commodityAttrMappingId) {
		this.commodityAttrMappingId = commodityAttrMappingId;
	}
	public CommodityVO getCommodityVO() {
		return commodityVO;
	}
	public void setCommodityVO(CommodityVO commodityVO) {
		this.commodityVO = commodityVO;
	}
	public CommodityAttrVO getCommodityAttrVO() {
		return commodityAttrVO;
	}
	public void setCommodityAttrVO(CommodityAttrVO commodityAttrVO) {
		this.commodityAttrVO = commodityAttrVO;
	}	
	public String getCommodityAttrValue() {
		return commodityAttrValue;
	}
	public void setCommodityAttrValueValue(String commodityAttrValue) {
		this.commodityAttrValue = commodityAttrValue;
	}
	
	@Override
	public String toString() {
		return "CommodityAttrMappingVO [commodityAttrMappingId=" + commodityAttrMappingId + ", commodityVO="
				+ commodityVO + ", commodityAttrVO=" + commodityAttrVO + ", commodityAttrValue=" + commodityAttrValue + "]";
	}	

}
