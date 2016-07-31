package userConfig.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserConfigController {
	private static Log log = LogFactory.getLog(UserConfigController.class);
	
	@RequestMapping(value="/userConfig", method=RequestMethod.GET)
	public String getUserConfig () {
		System.out.println("123");
		return "/userConfig/list";
	}

}
