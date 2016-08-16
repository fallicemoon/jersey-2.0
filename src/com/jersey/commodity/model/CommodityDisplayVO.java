package com.jersey.commodity.model;

import java.util.Map;

public class CommodityDisplayVO extends CommodityVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4286339903266668677L;
	
	private Integer pictureCount;
	private String commodityType;
	private Map<String, String> commodityAttrValueMap;
	

	public Integer getPictureCount() {
		return pictureCount;
	}

	public void setPictureCount(Integer pictureCount) {
		this.pictureCount = pictureCount;
	}

	public String getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}

	public Map<String, String> getCommodityAttrValueMap() {
		return commodityAttrValueMap;
	}

	public void setCommodityAttrValueMap(Map<String, String> commodityAttrValueMap) {
		this.commodityAttrValueMap = commodityAttrValueMap;
	}
	

}
