package com.jersey.userConfig.model;

import java.util.LinkedHashSet;
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
	private Set<CommodityAttrMappingVO> commodityAttrMappings = new LinkedHashSet<>();
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commodityAttrId == null) ? 0 : commodityAttrId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommodityAttrVO other = (CommodityAttrVO) obj;
		if (commodityAttrId == null) {
			if (other.commodityAttrId != null)
				return false;
		} else if (!commodityAttrId.equals(other.commodityAttrId))
			return false;
		return true;
	}
	

	
	
}
