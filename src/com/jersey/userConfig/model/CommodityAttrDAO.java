package com.jersey.userConfig.model;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;
import com.jersey.tools.JerseyEnum.Authority;
import com.jersey.tools.JerseyEnum.CommodityAttrAuthority;

@Repository
public class CommodityAttrDAO extends AbstractDAO<CommodityAttrVO> {

	public CommodityAttrDAO() {
		super(CommodityAttrVO.class, "commodityAttrId");
	}
	
	public List<CommodityAttrVO> getCommodityAttrByCommodityType(Authority authority, CommodityTypeVO commodityTypeVO) {
		if (Authority.admin == authority) {
			return getHelper(Restrictions.eq("commodityTypeVO", commodityTypeVO));
		} else {
			return getHelper(Restrictions.eq("commodityTypeVO", commodityTypeVO),
					Restrictions.eq("commodityAttrAuthority", CommodityAttrAuthority.customer));
		}
	}
	
	public CommodityAttrVO getByCommodityAttrName (String commodityAttr) {
		List<CommodityAttrVO> list = getHelper(Restrictions.eq("commodityAttr", commodityAttr));
		if (list!=null && list.size()!=0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	
}
