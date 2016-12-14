package com.jersey.userConfig.model;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;
import com.jersey.tools.JerseyEnum.Authority;
import com.jersey.tools.JerseyEnum.CommodityAttrAuthority;

@Repository
public class CommodityAttrDAO extends AbstractDAO<CommodityAttrVO> {

	public CommodityAttrDAO() {
		super(CommodityAttrVO.class);
	}

	public List<CommodityAttrVO> getCommodityAttrByCommodityType(Authority authority, CommodityTypeVO commodityTypeVO) {
		if (Authority.ADMIN == authority) {
			return getHelper(Order.asc("id"), Restrictions.eq("commodityTypeVO", commodityTypeVO));
		} else {
			return getHelper(Restrictions.eq("commodityTypeVO", commodityTypeVO),
					Restrictions.eq("commodityAttrAuthority", CommodityAttrAuthority.CUSTOMER));
		}
	}

}
