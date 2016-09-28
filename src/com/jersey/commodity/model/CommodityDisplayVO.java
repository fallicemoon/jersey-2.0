package com.jersey.commodity.model;

import java.util.List;

public class CommodityDisplayVO extends CommodityVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4286339903266668677L;
	
	private Integer pictureCount;
	private List<CommodityAttrValueVO> commodityAttrValueList;
	

	public Integer getPictureCount() {
		return pictureCount;
	}

	public void setPictureCount(Integer pictureCount) {
		this.pictureCount = pictureCount;
	}

	public List<CommodityAttrValueVO> getCommodityAttrValueList() {
		return commodityAttrValueList;
	}

	public void setCommodityAttrValueList(List<CommodityAttrValueVO> commodityAttrValueList) {
		this.commodityAttrValueList = commodityAttrValueList;
	}



	

}
