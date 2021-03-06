package com.jersey.picture.model;

import java.sql.Blob;

import com.jersey.commodity.model.CommodityVO;
import com.jersey.tools.AbstractVo;

public class PictureVO extends AbstractVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6563278643281171369L;
	private CommodityVO commodityVO;
	private Integer sequenceId;
	private Blob picture;
	private String fileName;

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public CommodityVO getCommodityVO() {
		return this.commodityVO;
	}

	public void setCommodityVO(CommodityVO commodityVO) {
		this.commodityVO = commodityVO;
	}

	public Integer getSequenceId() {
		return this.sequenceId;
	}

	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Blob getPicture() {
		return this.picture;
	}

	public void setPicture(Blob picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "PictureVO [pictureId=" + getId() + ", commodityVO=" + commodityVO + ", sequenceId=" + sequenceId
				+ ", fileName=" + fileName + ", getCreateTime()=" + getCreateTime() + ", getLastModifyTime()="
				+ getLastModifyTime() + "]";
	}


	
	
}
