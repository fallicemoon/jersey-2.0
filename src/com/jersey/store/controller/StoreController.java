package com.jersey.store.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jersey.store.model.StoreService;
import com.jersey.store.model.StoreVO;
import com.jersey.tools.Tools;

@Controller
@RequestMapping(value="/store")
public class StoreController {
	private static final String LIST = "store/list";
	private static final String ADD = "store/add";
	private static final String UPDATE = "store/update";
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private StoreService storeService;
	
	//for update store用
	@ModelAttribute
	public void getStore (Map<String, Object> map, @PathVariable Map<String, String> pathVariableMap) {
		Set<String> keySet = pathVariableMap.keySet();
		if(keySet.contains("id")){
			String storeId = pathVariableMap.get("id");
			map.put("storeVO", storeService.getOne(Integer.valueOf(storeId)));
		}
	}
	
	//取得全部
	@RequestMapping(value="/getAll", method=RequestMethod.GET)
	public String getAll(Map<String, Object> map){
		map.put("storeList", storeService.getAll());
		return LIST;
	}
	
	//準備新增
	@RequestMapping(value="", method=RequestMethod.GET)
	public String get(Map<String, Object> map){
		return ADD;
	}
	
	//準備更新
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String getOne (@PathVariable("id") Integer id, Map<String, Object> map) {
		return UPDATE;
	}
	
	//新增
	@RequestMapping(value="", method=RequestMethod.POST)
	public String create (StoreVO vo, Map<String, Object> map) {
		storeService.create(vo);
		List<StoreVO> list = new ArrayList<>();
		list.add(vo);
		map.put("storeList", list);
		//一次只能一個人動商店和托運公司清單
		synchronized (this) {
			//增加servletContext的store清單
			Set<StoreVO> storeList = (Set<StoreVO>) servletContext.getAttribute(vo.getType().toString());
			storeList.add(vo);
			servletContext.setAttribute(vo.getType().toString(), storeList);
		}
		return LIST;
	}
	
	//修改
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String update (@PathVariable("id") Integer id, @ModelAttribute(value="store") StoreVO vo, Map<String, Object> map) {
		storeService.update(vo);
		List<StoreVO> list = new ArrayList<>();
		list.add(vo);
		map.put("purchaseCaseList", list);
		//一次只能一個人動商店和托運公司清單
		synchronized (this) {
			//增加servletContext的store清單
			Set<StoreVO> storeList = (Set<StoreVO>) servletContext.getAttribute(vo.getType().toString());
			storeList.add(vo);
			servletContext.setAttribute(vo.getType().toString(), storeList);
		}
		return LIST;
	}
	
	//刪除多筆
	@ResponseBody
	@RequestMapping(value="", method=RequestMethod.PUT)
	public String delete (@RequestBody String json) {
		try {
			JSONArray jsonArray = new JSONArray(json);
			List<Object> storeIds = jsonArray.toList();
			Integer[] ids = new Integer[storeIds.size()];
			for (int i = 0; i < storeIds.size(); i++) {
				ids[i] = Integer.valueOf(storeIds.get(i).toString());
			}
			storeService.delete(ids);
			return Tools.getSuccessJson().toString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return Tools.getFailJson().toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return Tools.getFailJson().toString();			
		}
	}

}
