package com.jersey.member.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jersey.member.model.MemberService;
import com.jersey.member.model.MemberVO;
import com.jersey.userConfig.model.UserSession;

@Controller
@SessionAttributes(value={"commodityTypeVOList"})
public class MemberController {
	
	private static final String INDEX = "index";
	private static final String LOGIN = "login";
	private static final String REDIRECT_INDEX = "redirect:index.html";
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private UserSession userSession;
	
	//秘密接口, 只有admin才會知道
	@RequestMapping(value="/secretLogin", method=RequestMethod.POST)
	public String login (MemberVO memberVO, Map<String, Object> map, HttpServletRequest request) {
		boolean result = memberService.login(memberVO);
		if (result) {
			//登入後更換sessionId
			Map<String, Object> sessionAttributes = new HashMap<>();
			Enumeration<String> enumeration = request.getSession().getAttributeNames();
			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement();
				sessionAttributes.put(key, request.getSession().getAttribute(key));
			}
			request.getSession().invalidate();
			for (String key : sessionAttributes.keySet()) {
				request.getSession().setAttribute(key, sessionAttributes.get(key));
			}
			
			return REDIRECT_INDEX;
		} else {
			map.put("errorMessage", "帳號密碼打錯了");
			return LOGIN;
		}
	}
	
	//登出
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout (HttpSession session) {
		session.invalidate();
		return REDIRECT_INDEX;
	}
	
	//導向首頁
	@RequestMapping(value="/index.html", method=RequestMethod.GET)
	public String welcome (Map<String, Object> map) {
		if (userSession.isAdmin()) {
			//賣家, 可以使用完整功能
			map.put("commodityTypeVOList", userSession.getCommodityTypeAttrMap().keySet());
		} else {
			//一般使用者
			map.put("commodityTypeVOList", userSession.getCommodityTypeAttrMap().keySet());
		}
		return INDEX;
	}

}
