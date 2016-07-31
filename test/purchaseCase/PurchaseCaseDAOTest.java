package purchaseCase;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import purchaseCase.model.PurchaseCaseDAO;
import purchaseCase.model.PurchaseCaseVO;

public class PurchaseCaseDAOTest {
	
	private PurchaseCaseDAO purchaseCaseDAO;
	private List<PurchaseCaseVO> list;
	
	@Before
	public void init () {
		purchaseCaseDAO = new PurchaseCaseDAO();
	}
	
	@Test
	public void getAllOfNotProgress () {
		list = purchaseCaseDAO.getAllOfNotProgress("進貨完成");
	}
	
	@Test
	public void getPurchaseCasesBySellCaseIdIsNull () {
		list = purchaseCaseDAO.getPurchaseCasesBySellCaseIdIsNull();
	}
	
	
	
	@After
	public void destroy () {
		for (PurchaseCaseVO purchaseCaseVO : list) {
			System.out.println(purchaseCaseVO);
		}
	} 

}
