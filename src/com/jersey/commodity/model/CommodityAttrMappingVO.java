package com.jersey.commodity.model;

import com.jersey.tools.AbstractVo;
import com.jersey.userConfig.model.CommodityAttrVO;

public class CommodityAttrMappingVO extends AbstractVo implements Comparable<CommodityAttrMappingVO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1103527345568677121L;
	
	private CommodityVO commodityVO;
	private CommodityAttrVO commodityAttrVO;
	private String commodityAttrValue;
	
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
	public void setCommodityAttrValue(String commodityAttrValue) {
		this.commodityAttrValue = commodityAttrValue;
	}
	
	@Override
	public String toString() {
		return "CommodityAttrMappingVO [commodityAttrMappingId=" + getId() + ", commodityVO="
				+ commodityVO + ", commodityAttrVO=" + commodityAttrVO + ", commodityAttrValue=" + commodityAttrValue + "]";
	}
	@Override
	public int compareTo(CommodityAttrMappingVO o) {
		//commodity裡面的屬性值是用SortedSet來排序
		return this.commodityAttrVO.getId().compareTo(o.commodityAttrVO.getId());
		
	}

}
