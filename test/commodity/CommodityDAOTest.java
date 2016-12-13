package commodity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.jersey.commodity.model.CommodityDAO;
import com.jersey.commodity.model.CommodityVO;
import com.jersey.tools.JerseyEnum.PrimaryKey;

import sun.net.www.content.text.plain;

public class CommodityDAOTest {
	
	private CommodityDAO commodityDAO;
	private List<CommodityVO> list;
	
	@Before
	public void init () {
		commodityDAO = new CommodityDAO();
	}
	
	@Test
	public void getByPurchaseCaseIdIsNull () {
		Map<Class<?>, String> map = new HashMap<>();
		map.put(Exception.class, Exception.class.getName());
		map.put(RuntimeException.class, RuntimeException.class.getName());
		//map.put(ArithmeticException.class, ArithmeticException.class.getName());
		System.out.println(map.get(ArithmeticException.class));
	}
	
	@Test
	public void getOne () {
		System.out.println(StringUtils.leftPad("", 6, "9"));
	}
	


}
