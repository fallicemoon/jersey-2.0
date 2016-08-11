package userConfig.model;

import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;

@Repository
public class UserConfigDAO extends AbstractDAO<UserConfigVO> {

	public UserConfigDAO() {
		super(UserConfigVO.class, "userConfigId");
	}
	
}
