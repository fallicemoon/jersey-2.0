package userConfig.model;

import java.io.Serializable;

import org.json.JSONObject;

public class UserConfigWithJsonVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8970990014946380210L;
	
	private Integer userConfigId;
	private JSONObject configJson;

	public Integer getUserConfigId() {
		return userConfigId;
	}

	public void setUserConfigId(Integer userConfigId) {
		this.userConfigId = userConfigId;
	}

	public JSONObject getConfigJson() {
		return configJson;
	}

	public void setConfigJson(JSONObject configJson) {
		this.configJson = configJson;
	}
	
	

}
