package picture;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import picture.model.PictureDAO;
import picture.model.PictureVO;

public class PictureDAOTest {
	
	private PictureDAO pictureDAO;
	private List<PictureVO> list;
	
	@Before
	public void Init () {
		pictureDAO = new PictureDAO();
	}
	
	
	@Test
	public void getPictureIds () {
		list = pictureDAO.getPictureIds(28);
		System.out.println(list);
	}
	
	@Test
	public void getPicturesByCommodityId () {
		list = pictureDAO.getPicturesByCommodityId(50);
		System.out.println(list);
	}
	
	@Test
	public void getCommodityIdPictureCountMap () {
		Map<Integer, Integer> map = pictureDAO.getCommodityIdPictureCountMap();
		System.out.println(map);
	}
	
	@Test
	public void getCommodityIdPictureCount () {
		Integer count = pictureDAO.getCommodityIdPictureCount(28);
		System.out.println(count);
	}
	

}
