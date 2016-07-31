package commodity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import commodity.model.CommodityDAO;
import commodity.model.CommodityVO;

public class CommodityDAOTest {
	
	private CommodityDAO commodityDAO;
	private List<CommodityVO> list;
	
	@Before
	public void init () {
		commodityDAO = new CommodityDAO();
	}
	
	@Test
	public void getByRule () {
		Map<String, Object> rule = new HashMap<>();
		rule.put("style", "Road");
		list = commodityDAO.getByRule(rule);
		System.out.println(list);
	}
	
	@Test
	public void getByPurchaseCaseIdIsNull () {
		list = commodityDAO.getByPurchaseCaseIdIsNull();
		System.out.println(list);
	}

}
