package sellCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sellCase.model.SellCaseDAO;
import sellCase.model.SellCaseVO;

public class SellCaseDAOTest {
	
	private SellCaseDAO sellCaseDAO;
	private List<SellCaseVO> list;
	
	@Before
	public void init () {
		sellCaseDAO = new SellCaseDAO();
	}
	
	@Test
	public void getUncollectedNotZero () {
		list = sellCaseDAO.getUncollectedNotZero();
	}
	
	@Test
	public void getBetweenCloseTime () {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date start = df.parse("2015-03-25 00:00:00");
			Date end = df.parse("2015-03-26 00:00:00");
			list = sellCaseDAO.getBetweenCloseTime(start, end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getNotClosed () {
		list = sellCaseDAO.getNotClosed();
	}
	
	@Test
	public void getIsClosed () {
		list = sellCaseDAO.getIsClosed();
	}
	
	
	
	@After
	public void destroy () {
		System.out.println(list);
	}
	

}
