package com.jersey.commodity.model;

import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;

@Repository
public class CommodityAttrMappingDAO extends AbstractDAO<CommodityAttrMappingVO> {

	public CommodityAttrMappingDAO() {
		super(CommodityAttrMappingVO.class, "commodityAttrMappingId");
		// TODO Auto-generated constructor stub
	}

}
