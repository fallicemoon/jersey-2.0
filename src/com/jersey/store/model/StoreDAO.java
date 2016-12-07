package com.jersey.store.model;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;
import com.jersey.tools.JerseyEnum.StoreType;

@Repository
public class StoreDAO extends AbstractDAO<StoreVO> {

	public StoreDAO() {
		super(StoreVO.class, "storeId");
	}

	public List<StoreVO> getStoreListByType(StoreType type) {
		return getHelper(Restrictions.eq("type", type));
	}

}
