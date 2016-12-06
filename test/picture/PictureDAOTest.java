package picture;

import java.util.List;

import org.junit.Before;

import com.jersey.picture.model.PictureDAO;
import com.jersey.picture.model.PictureVO;

public class PictureDAOTest {
	
	private PictureDAO pictureDAO;
	private List<PictureVO> list;
	
	@Before
	public void Init () {
		pictureDAO = new PictureDAO();
	}
	
	

}
