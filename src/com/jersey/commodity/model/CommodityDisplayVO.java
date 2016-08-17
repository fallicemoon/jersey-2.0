package com.jersey.commodity.model;

public class CommodityDisplayVO extends CommodityVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4286339903266668677L;
	
	private Integer pictureCount;
	private CommodityAttrValueVO commodityAttrValueVO;
	

	public Integer getPictureCount() {
		return pictureCount;
	}

	public void setPictureCount(Integer pictureCount) {
		this.pictureCount = pictureCount;
	}

	public CommodityAttrValueVO getCommodityAttrValueVO() {
		return commodityAttrValueVO;
	}

	public void setCommodityAttrValueVO(CommodityAttrValueVO commodityAttrValueVO) {
		this.commodityAttrValueVO = commodityAttrValueVO;
	}


	

}
