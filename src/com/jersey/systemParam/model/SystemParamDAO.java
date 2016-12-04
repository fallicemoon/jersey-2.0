package com.jersey.systemParam.model;

import com.jersey.tools.AbstractDAO;

public class SystemParamDAO extends AbstractDAO<SystemParamVO> {

	public SystemParamDAO(Class<SystemParamVO> type, String pk) {
		super(SystemParamVO.class, "name");
	}
	
	

}
