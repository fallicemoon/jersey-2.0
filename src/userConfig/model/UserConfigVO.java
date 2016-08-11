package userConfig.model;

import java.sql.Clob;

import com.jersey.tools.AbstractVo;

public class UserConfigVO extends AbstractVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8297622549211793706L;
	
	private Integer userConfigId;
	private Clob config;
	public Integer getUserConfigId() {
		return userConfigId;
	}
	public void setUserConfigId(Integer userConfigId) {
		this.userConfigId = userConfigId;
	}
	public Clob getConfig() {
		return config;
	}
	public void setConfig(Clob config) {
		this.config = config;
	}
	
	

}
