package commodity;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jersey.commodity.model.CommodityDAO;
import com.jersey.commodity.model.CommodityVO;

public class CommodityDAOTest {
	
	private CommodityDAO commodityDAO;
	private List<CommodityVO> list;
	
	@Before
	public void init () {
		commodityDAO = new CommodityDAO();
	}
	
	@Test
	public void getByPurchaseCaseIdIsNull () {
		list = commodityDAO.getByPurchaseCaseIdIsNull();
		System.out.println(list);
	}
	
	@Test
	public void getOne () {
		System.out.println(commodityDAO.getOne(85));
	} 

}
