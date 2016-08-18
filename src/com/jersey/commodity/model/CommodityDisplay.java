package com.jersey.commodity.model;

import java.util.List;

public class CommodityDisplay extends CommodityVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4286339903266668677L;
	
	private Integer pictureCount;
	private List<CommodityAttrValue> commodityAttrValueList;
	

	public Integer getPictureCount() {
		return pictureCount;
	}

	public void setPictureCount(Integer pictureCount) {
		this.pictureCount = pictureCount;
	}

	public List<CommodityAttrValue> getCommodityAttrValueList() {
		return commodityAttrValueList;
	}

	public void setCommodityAttrValueList(List<CommodityAttrValue> commodityAttrValueList) {
		this.commodityAttrValueList = commodityAttrValueList;
	}



	

}
