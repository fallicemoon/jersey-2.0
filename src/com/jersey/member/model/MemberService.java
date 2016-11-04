package com.jersey.member.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.userConfig.model.UserConfigService;
import com.jersey.userConfig.model.UserSession;

@Service
public class MemberService {
	
	@Autowired
	UserSession userSession;
	@Autowired
	UserConfigService userConfigService;
	
	public boolean login (MemberVO memberVO) {
		//TODO 帳號驗證, 多用戶
		if ("jersey".equals(memberVO.getUserName()) && "white".equals(memberVO.getPassword())) {
			userConfigService.initAdminUserSessionUserConfig("jersey");
			return true;
		} else {
			return false;
		}
	}
	

}
