package com.jersey.store.controller;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jersey.store.model.StoreService;
import com.jersey.store.model.StoreVO;
import com.jersey.tools.JerseyEnum.StoreType;
import com.jersey.tools.JerseyTools;
import com.jersey.userConfig.model.UserSession;

@Controller
@RequestMapping(value="/store")
public class StoreController {
	private static final String LIST = "store/list";
	private static final String ADD = "store/add";
	private static final String UPDATE = "store/update";
	private static final String REDIRECT_LIST = "redirect:getAll";
	
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private StoreService storeService;
	@Autowired
	private UserSession userSession;
	
	//for update store用
	@ModelAttribute
	public void getStore (Map<String, Object> map, @PathVariable Map<String, String> pathVariableMap) {
		Set<String> keySet = pathVariableMap.keySet();
		if(keySet.contains("id")){
			String storeId = pathVariableMap.get("id");
			map.put("store", storeService.getOne(storeId));
		}
	}
	
	//取得全部
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String getAll(Map<String, Object> map,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
		map.put("storeList", storeService.getAll(userSession.getUserConfigVO().getStorePageSize(), page));
		Long count = storeService.getTotalCount()/userSession.getUserConfigVO().getStorePageSize();
		if (storeService.getTotalCount()%userSession.getUserConfigVO().getStorePageSize()!=0) {
			count++;
		}
		map.put("pages", count);
		return LIST;
	}
	
	//準備新增
	@RequestMapping(value="", method=RequestMethod.GET)
	public String get(Map<String, Object> map){
		return ADD;
	}
	
	//準備更新
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String getOne (@PathVariable("id") String id, Map<String, Object> map) {
		return UPDATE;
	}
	
	//新增
	@RequestMapping(value="", method=RequestMethod.POST)
	public String create (StoreVO vo, Map<String, Object> map) {
		storeService.create(vo);
//		List<StoreVO> list = new ArrayList<>();
//		list.add(vo);
//		map.put("storeList", list);
		//一次只能一個人動商店和托運公司清單
		synchronized (this) {
			//增加servletContext的store清單
			String name = vo.getType()==StoreType.STORE?"store":"shippingCompany";
			Set<StoreVO> storeList = (Set<StoreVO>) servletContext.getAttribute(name);
			storeList.add(vo);
			servletContext.setAttribute(name, storeList);
		}
		return REDIRECT_LIST;
	}
	
	//修改
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String update (@PathVariable("id") String id, @ModelAttribute(value="store") StoreVO vo, Map<String, Object> map) {
		storeService.update(vo);
//		List<StoreVO> list = new ArrayList<>();
//		list.add(vo);
//		map.put("storeList", list);
		//一次只能一個人動商店和托運公司清單
		synchronized (this) {
			//增加servletContext的store清單
			String name = vo.getType()==StoreType.STORE?"store":"shippingCompany";
			Set<StoreVO> storeList = (Set<StoreVO>) servletContext.getAttribute(name);
			storeList.add(vo);
			servletContext.setAttribute(name, storeList);
		}
		return REDIRECT_LIST;
	}
	
	// 刪除多筆
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT, produces="application/json;charset=UTF-8")
	public String delete(@RequestBody String[] storeIds) {
		try {
			Integer[] ids = new Integer[storeIds.length];
			for (int i = 0; i < storeIds.length; i++) {
				ids[i] = Integer.valueOf(storeIds[i]);
			}
			if (!storeService.delete(ids)) {
				throw new Exception();
			}
			servletContext.setAttribute("store", storeService.getStoreSetByType(StoreType.STORE));
			servletContext.setAttribute("shippingCompany", storeService.getStoreSetByType(StoreType.SHIPPING_COMPANY));
			return JerseyTools.getSuccessJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return JerseyTools.getFailJson("刪除失敗").toString();
		}
	}

}
