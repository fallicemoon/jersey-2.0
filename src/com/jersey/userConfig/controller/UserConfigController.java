package com.jersey.userConfig.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import userConfig.model.UserConfigService;

@Controller
@RequestMapping("/userConfig")
public class UserConfigController {
	private static Log log = LogFactory.getLog(UserConfigController.class);
	
	@Autowired
	private UserConfigService userConfigService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String getUserConfig () {
		
		return "/userConfig/list";
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String createUserConfig () {
		
		return "/userConfig/list";
	}

}
