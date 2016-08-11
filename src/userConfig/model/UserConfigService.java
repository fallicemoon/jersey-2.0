package userConfig.model;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.tools.HibernateSessionFactory;
import com.jersey.tools.Tools;

@Service
public class UserConfigService {

	@Autowired
	private UserConfigDAO userConfigDAO;

	public List<UserConfigWithJsonVO> getAll() {
		List<UserConfigVO> oldList = userConfigDAO.getAll();
		List<UserConfigWithJsonVO> newList = new ArrayList<>();
		for (UserConfigVO userConfigVO : oldList) {
			try {
				newList.add(parseClobToJson(userConfigVO));
			} catch (SQLException | IOException e) {
				e.printStackTrace();
				continue;
			}
		}
		return newList;
	}

	public UserConfigWithJsonVO getOne(Integer id) throws SQLException, IOException {
		return parseClobToJson(userConfigDAO.getOne(id));
	}

	public void create(UserConfigWithJsonVO vo) {
		try {
			userConfigDAO.create(parseJsonToClob(vo));
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	public void update(UserConfigWithJsonVO vo) {
		try {
			userConfigDAO.update(parseJsonToClob(vo));
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(Integer... ids) {
		userConfigDAO.delete(ids);
	}

	private UserConfigWithJsonVO parseClobToJson(UserConfigVO userConfigVO) throws SQLException, IOException {
		UserConfigWithJsonVO userConfigWithJsonVO = new UserConfigWithJsonVO();
		//CLOB不要複製, 怕太吃資源
		Tools.copyBeanProperties(userConfigVO, userConfigWithJsonVO);
		
		Reader reader = userConfigVO.getConfig().getCharacterStream();
		StringWriter writer = new StringWriter();
		char[] c = new char[1024];
		int length = -1;
		while ((length = reader.read(c)) != -1) {
			writer.write(c, 0, length);
		}
		String config = writer.toString();
		userConfigWithJsonVO.setConfigJson(new JSONObject(config));
		return userConfigWithJsonVO;
	}
	
	private UserConfigVO parseJsonToClob(UserConfigWithJsonVO userConfigWithJsonVO) throws SQLException, IOException {
		UserConfigVO userConfigVO = new UserConfigVO();
		Tools.copyBeanProperties(userConfigWithJsonVO, userConfigVO);
		Clob clob = Hibernate.getLobCreator(HibernateSessionFactory.getSession()).createClob(userConfigWithJsonVO.getConfigJson().toString());
		userConfigVO.setConfig(clob);
		return userConfigVO;
	}

}
