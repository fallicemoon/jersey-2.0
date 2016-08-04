package com.jersey.store.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.tools.JerseyEnum.StoreType;

@Service
public class StoreService {
	
	@Autowired
	private StoreDAO storeDAO;
	
	
	public List<StoreVO> getAll() {
		return storeDAO.getAll();
	}
	
	public StoreVO getOne(Integer id) {
		return storeDAO.getOne(id);
	}
	
	public List<StoreVO> getStoreListByType(StoreType type) {
		return storeDAO.getStoreListByType(type);
	}
	
	public Set<StoreVO> getStoreSetByType(StoreType type) {
		List<StoreVO> storeList = storeDAO.getStoreListByType(type);
		return new HashSet<StoreVO>(storeList);
	}
	
	public void create(StoreVO vo) {
		storeDAO.create(vo);
	}
	
	public void update(StoreVO vo) {
		storeDAO.update(vo);
	}
	
	public void delete(Integer... ids) {
		storeDAO.delete(ids);
	}
	
	
	
}

