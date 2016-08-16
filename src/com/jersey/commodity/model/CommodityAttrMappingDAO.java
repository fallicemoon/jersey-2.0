package com.jersey.commodity.model;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;

@Repository
public class CommodityAttrMappingDAO extends AbstractDAO<CommodityAttrMappingVO> {

	public CommodityAttrMappingDAO() {
		super(CommodityAttrMappingVO.class, "commodityAttrMappingId");
	}
	
	public List<CommodityAttrMappingVO> getByCommodityVO (CommodityVO commodityVO) {
		return getHelper(Restrictions.eq("commodityVO", commodityVO));
	}
	
	public List<CommodityAttrMappingVO> getByCommodityVO (List<CommodityVO> commodityList) {
		return getHelper(Restrictions.in("commodityVO", commodityList));
	}

}
