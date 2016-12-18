package com.jersey.commodity.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jersey.commodity.model.CommodityAttrMappingVO;
import com.jersey.commodity.model.CommodityService;
import com.jersey.commodity.model.CommodityVO;
import com.jersey.tools.JerseyEnum.Authority;
import com.jersey.tools.JerseyTools;
import com.jersey.userConfig.model.CommodityAttrVO;
import com.jersey.userConfig.model.CommodityTypeVO;
import com.jersey.userConfig.model.UserSession;

@Controller
@RequestMapping(value = "/commodity")
public class CommodityController {
	private static final String LIST = "commodity/list";
	private static final String ADD = "commodity/add";
	private static final String REDIRECT_LIST = "redirect:getAll";

	@Autowired
	private CommodityService commodityService;
	@Autowired
	private UserSession userSession;

	//根據商品種類和登入者權限取得商品, 根據使用者權限取得商品屬性
	@RequestMapping(value = "/{commodityTypeId}/getAll", method = RequestMethod.GET)
	public String getAll(Map<String, Object> map, @PathVariable(value="commodityTypeId") String commodityTypeId,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
		Map<CommodityTypeVO, List<CommodityAttrVO>> commodityTypeAttrMap = userSession.getCommodityTypeAttrMap();
		CommodityTypeVO commodityTypeVO = new CommodityTypeVO();
		for (CommodityTypeVO vo : commodityTypeAttrMap.keySet()) {
			if (vo.getId().equals(commodityTypeId)) {
				commodityTypeVO = vo;
			}
		}
		
		map.put("commodityTypeId", commodityTypeId);
		map.put("commodityAttrList", commodityService.getVisibleCommodityAttr(commodityTypeVO));
		//ADMIN_HIDDEN在getDisplayVo有拿掉了，不用再拿
		map.put("commodityList", commodityService.getAll(commodityTypeVO, page));
		map.put("pages", commodityService.getPages(commodityTypeVO));
		return LIST;
	}

	// 準備新增
	@RequestMapping(value = "/{commodityTypeId}", method = RequestMethod.GET)
	public String get(@PathVariable(value="commodityTypeId") String commodityTypeId, Map<String, Object> map) {
		CommodityTypeVO commodityTypeVO = new CommodityTypeVO();
		commodityTypeVO.setId(commodityTypeId);
		map.put("commodityTypeId", commodityTypeId);
		return ADD;
	}

	// 準備更新
//	@RequestMapping(value = "/{commodityTypeId}/{id}", method = RequestMethod.GET)
//	public String getOne(@PathVariable(value="commodityTypeId") Integer commodityTypeId, @PathVariable("id") Integer id, Map<String, Object> map) {
//		CommodityTypeVO commodityTypeVO = new CommodityTypeVO();
//		commodityTypeVO.setCommodityTypeId(commodityTypeId);
//		map.put("commodityTypeId", commodityTypeId);
//		return UPDATE;
//	}

	// 新增
	@RequestMapping(value = "/{commodityTypeId}/", method = RequestMethod.POST)
	public String create(@PathVariable("commodityTypeId") String commodityTypeId, CommodityVO vo, Map<String, Object> map) {
		//新增商品
		vo.setIsStored(true);
		CommodityTypeVO commodityTypeVO = new CommodityTypeVO();
		commodityTypeVO.setId(commodityTypeId);
		vo.setCommodityTypeVO(commodityTypeVO);
		//避免使用者沒有傳入數字
		vo.setCost(vo.getCost()==null ? 0:vo.getCost());
		vo.setSellPrice(vo.getSellPrice()==null ? 0:vo.getSellPrice());
		//trim商品名稱
		vo.setItemName(StringUtils.trimToEmpty(vo.getItemName()));
		
		commodityService.create(vo);
		
//		List<CommodityDisplayVO> list = new ArrayList<>();
//		list.add(commodityService.getCommodityDisplayVO(vo));
//		map.put("commodityList", list);
//		map.put("commodityTypeId", commodityTypeId);
//		map.put("commodityAttrList", userSession.getCommodityTypeAttrMap().get(commodityTypeVO));
		return REDIRECT_LIST;
	}

	// 修改
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces="application/json;charset=UTF-8", consumes="application/json;charset=UTF-8")
	public @ResponseBody String update(@PathVariable("id") String id, @RequestBody String jsonString) {
		try {
			JSONObject json = new JSONObject(jsonString);
			CommodityVO commodityVO = commodityService.getOne(id);
			commodityVO.setItemName(StringUtils.isBlank(json.getString("itemName")) ? "":json.getString("itemName"));
			commodityVO.setLink(StringUtils.isBlank(json.getString("link")) ? "":json.getString("link"));
			try {
				commodityVO.setCost(json.getInt("cost"));
			} catch (Exception e) {
				commodityVO.setCost(0);
			}
			try {
				commodityVO.setSellPrice(json.getInt("sellPrice"));
			} catch (Exception e) {
				commodityVO.setCost(0);
			}
			commodityVO.setIsStored("否".equals(json.getString("isStored")) ? false:true);
			
			Set<CommodityAttrMappingVO> commodityAttrMappings = commodityVO.getCommodityAttrMappings();
			//commodityAttrMap此map key-value為commodityAttrMappingVO的id-value
			Map<String, Object> commodityAttrMap = json.getJSONObject("commodityAttr").toMap();
			for (CommodityAttrMappingVO commodityAttrMappingVO : commodityAttrMappings) {
				String commodityAttrMappingId = commodityAttrMappingVO.getId().toString();
				if (commodityAttrMap.keySet().contains(commodityAttrMappingId)){
					commodityAttrMappingVO.setCommodityAttrValue(StringUtils.trimToEmpty(commodityAttrMap.get(commodityAttrMappingId).toString()));
				}
			}
			commodityService.update(commodityVO);
			return JerseyTools.getSuccessJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return JerseyTools.getFailJson("此筆商品更新失敗").toString();
		}

	}
//	@RequestMapping(value = "/{commodityTypeId}/{id}", method = RequestMethod.PUT)
//	public String update(@PathVariable(value="commodityTypeId") Integer commodityTypeId, @PathVariable("id") Integer id, @ModelAttribute(value = "commodity") CommodityVO vo,
//			Map<String, Object> map) {
//		commodityService.update(vo);
//		List<CommodityDisplayVO> list = new ArrayList<>();
//		list.add(commodityService.getCommodityDisplayVO(vo));
//		map.put("commodityList", list);
//		return LIST;
//	}
	
	//商品上下架
	@RequestMapping(value="/switchStatus/{id}", method=RequestMethod.PUT, produces="application/json;charset=UTF-8" )
	public @ResponseBody String switchCommodity (@PathVariable("id") String id, @RequestBody String jsonString) {
		try {
			JSONObject json = new JSONObject(jsonString);
			Authority authority = Authority.valueOf(json.get("authority").toString());
			CommodityVO commodityVO = commodityService.getOne(id);
			if (commodityVO.getAuthority()!=authority) {
				commodityVO.setAuthority(authority);
				commodityService.update(commodityVO);
			}
			return JerseyTools.getSuccessJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return JerseyTools.getFailJson("此筆商品上下架切換失敗").toString();
		}
	}

	// 刪除多筆
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT, produces="application/json;charset=UTF-8")
	public String delete(@RequestBody String[] commodityIds) {
		try {
			Integer[] ids = new Integer[commodityIds.length];
			for (int i = 0; i < commodityIds.length; i++) {
				ids[i] = Integer.valueOf(commodityIds[i]);
			}
			if(!commodityService.delete(ids)){
				return JerseyTools.getFailJson("刪除失敗").toString();
			}
			return JerseyTools.getSuccessJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return JerseyTools.getFailJson("刪除失敗").toString();
		}
	}

	// 複製
	@RequestMapping(value = "/clone", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public @ResponseBody String clone(@RequestParam(value = "commodityIds", required = true) String id) {
		try {
			CommodityVO commodityVO = commodityService.getOne(id);
			
			//因為是複製, 所以把PK清掉
			CommodityVO clone = new CommodityVO();
			JerseyTools.copyAbstractVoProperties(commodityVO, clone, "id", "commodityAttrMappings");
			SortedSet<CommodityAttrMappingVO> commodityAttrMappings = new TreeSet<>();
			if (commodityVO.getCommodityAttrMappings()!=null) {
				for (CommodityAttrMappingVO oldVo : commodityVO.getCommodityAttrMappings()) {
					CommodityAttrMappingVO newVo = new CommodityAttrMappingVO();
					JerseyTools.copyAbstractVoProperties(oldVo, newVo, "id", "commodityVO");
					commodityAttrMappings.add(newVo);
				};
			}
			clone.setCommodityAttrMappings(commodityAttrMappings);
			
			clone = commodityService.create(clone);
			JSONObject json = JerseyTools.getSuccessJson();
			json.put("commodityId", clone.getId());
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return JerseyTools.getFailJson("伺服器忙碌中").toString();
		}
	}

}
