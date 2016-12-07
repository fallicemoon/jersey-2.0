package com.jersey.userConfig.model;

import java.util.Set;

import com.jersey.commodity.model.CommodityVO;
import com.jersey.tools.AbstractVo;
import com.jersey.tools.JerseyEnum.Authority;

public class CommodityTypeVO extends AbstractVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7806107519331559770L;
	
	private String commodityType;
	private Authority authority;
	private Set<CommodityVO> commoditys;
	private Set<CommodityAttrVO> commodityAttrs;
	
	public String getCommodityType() {
		return commodityType;
	}
	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}
	public Authority getAuthority() {
		return authority;
	}
	public void setAuthority(Authority authority) {
		this.authority = authority;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
		CommodityTypeVO other = (CommodityTypeVO) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	
}
