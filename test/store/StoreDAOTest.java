package store;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jersey.store.model.StoreDAO;
import com.jersey.store.model.StoreVO;
import com.jersey.tools.JerseyEnum.StoreType;

public class StoreDAOTest {
	
	private StoreDAO storeDAO;
	private List<StoreVO> list;
	
	@Before
	public void Init () {
		storeDAO = new StoreDAO();
	}
	
	@Test
	public void getStoreListByType () {
		list = storeDAO.getStoreListByType(StoreType.STORE);
	}
	
	@After
	public void destroy () {
		System.out.println(StoreType.valueOf("store"));
	} 

}
