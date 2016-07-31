package store.model;

import java.util.List;

import tools.JerseyEnum.StoreType;

public class StoreService {
	private StoreDAO storeDAO;
	
	public StoreService () {
		storeDAO = new StoreDAO();
	}
	
	public List<StoreVO> getAll() {
		return storeDAO.getAll();
	}
	
	public StoreVO getOne(Integer id) {
		return storeDAO.getOne(id);
	}
	
	public List<StoreVO> getStoreListByType(StoreType type) {
		return storeDAO.getStoreListByType(type);
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

