package com.jersey.picture.controller;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jersey.commodity.model.CommodityService;
import com.jersey.picture.model.PictureService;
import com.jersey.tools.Tools;

@Controller
@RequestMapping("/picture/{commodityId}")
public class PictureController {
	private final static String COMMODITY_ID = "{commodityId}";
	private final static String PICTURE_CONTENT_TYPE = "image/*";
	private final static String REDIRECT_PICTURE = "redirect:/picture/{commodityId}/getAll";
	private final static String UPLOAD_PICTURE = "picture/uploadPicture";

	@Autowired
	private PictureService pictureService;
	@Autowired
	private CommodityService commodityService;


	@RequestMapping(value = "/getOne/{pictureId}", method = RequestMethod.GET)
	public void getPicture(@PathVariable("pictureId") Integer pictureId, HttpServletResponse response) {
		response.setContentType(PICTURE_CONTENT_TYPE);
		try {
			if (pictureService.validateReadPicAuthority(pictureId)) {
				pictureService.getPicrture(pictureId, response.getOutputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String getAll(@PathVariable("commodityId") Integer commodityId, Map<String, Object> map) {
		if (pictureService.validatePicPageAuthority(commodityId)) {
			Set<Integer> pictureIds = pictureService.getPictureIds(commodityId);
			map.put("pictureIds", pictureIds);
			map.put("commodity", commodityService.getOne(commodityId));
		}
		return UPLOAD_PICTURE;
	}

	@RequestMapping(value = "/uploadPicture", method = RequestMethod.POST)
	public String uploadPicture(@PathVariable("commodityId") Integer commodityId, HttpServletRequest request,
			Map<String, Object> map) {
		if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
			Set<String> errors = new LinkedHashSet<>();
			try {
				pictureService.uploadPicture(request);
				return REDIRECT_PICTURE.replace(COMMODITY_ID, commodityId.toString());
			} catch (SizeLimitExceededException e) {
				e.printStackTrace();
				errors.add("上傳檔案需小於30MB!");
			} catch (FileUploadException e) {
				e.printStackTrace();
				errors.add(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				errors.add("伺服器忙碌中, 請稍後再試");
			}
		}
		// exception 導回頁面取得所有圖片
		Set<Integer> pictureIds = pictureService.getPictureIds(commodityId);
		map.put("pictureIds", pictureIds);
		map.put("commodity", commodityService.getOne(commodityId));
		return UPLOAD_PICTURE;
	}
	
	//刪除多筆
	@ResponseBody
	@RequestMapping(value="", method=RequestMethod.PUT, produces="application/json;charset=UTF-8")
	public String delete (@RequestBody String json) {
		try {
			JSONArray jsonArray = new JSONArray(json);
			List<Object> storeIds = jsonArray.toList();
			Integer[] ids = new Integer[storeIds.size()];
			for (int i = 0; i < storeIds.size(); i++) {
				ids[i] = Integer.valueOf(storeIds.get(i).toString());
			}
			if (pictureService.deletePictures(ids)) {
				throw new Exception();
			}
			return Tools.getSuccessJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Tools.getFailJson("刪除失敗").toString();
		}
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download (@PathVariable("commodityId") Integer commodityId, @RequestParam("pictureIds") String[] pictureIds, HttpServletResponse response) {
		response.setHeader("Content-disposition", "attachment; filename=" + commodityId + ".zip");
		try {
			pictureService.getPicturesZip(pictureIds, response.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/downloadAll", method = RequestMethod.GET)
	public void downloadAll (@PathVariable("commodityId") Integer commodityId, HttpServletResponse response) {
		response.setHeader("Content-disposition", "attachment; filename=" + commodityId + ".zip");
		try {
			pictureService.getPicturesZip(commodityId, response.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
