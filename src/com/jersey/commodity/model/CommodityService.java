package com.jersey.commodity.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.picture.model.PictureDAO;
import com.jersey.tools.Tools;
import com.jersey.userConfig.model.CommodityAttrVO;
import com.jersey.userConfig.model.CommodityTypeVO;
import com.jersey.userConfig.model.UserConfigService;

@Service
public class CommodityService {

	
	@Autowired
	private CommodityDAO commodityDAO;
	@Autowired
	private UserConfigService userConfigService;
	
	/**
	 * 取得所有商品種類
	 * @return 此List為LinkedHashSet轉成, 順序就是從DB中撈出來的順序
	 */
	public List<CommodityTypeVO> getCommodityType () {
		return new ArrayList<>(userConfigService.getCommodityTypeAttrMap().keySet());
	}
	
	/**
	 * 取得某商品種類的所有商品屬性
	 * @param commodityTypeId
	 * @return
	 */
	public List<CommodityAttrVO> getCommodityAttrByCommodityTypeId (String commodityTypeId) {
		CommodityTypeVO commodityTypeVO = new CommodityTypeVO();
		return userConfigService.getCommodityTypeAttrMap().get(commodityTypeVO);
	}


	//取得所有商品
	public List<CommodityDisplayVO> getAll(String commodityType, Integer page) {
		return commodityDAO.getAll(userConfigService.getAuthority(), commodityType, userConfigService.getCommodityPageSize(), page);
	}
	
	//取得總分頁數
	public Long getPages (String commodityType) {
		Long pages = getTotalCount(commodityType)/userConfigService.getCommodityPageSize();
		if (getTotalCount(commodityType)%userConfigService.getCommodityPageSize()!=0) {
			pages++;
		}
		return pages;
	}
	
	//取得資料總筆數
	public Long getTotalCount (String commodityType) {
		return commodityDAO.getTotalCount(userConfigService.getAuthority(), commodityType);
	}

	public CommodityVO getOne(Integer commodityId) {
		return this.commodityDAO.getOne(userConfigService.getAuthority(), commodityId);
	}

	public CommodityVO create(CommodityVO vo) {
		return this.commodityDAO.create(vo);
	}

	public CommodityVO update(CommodityVO vo) {
		return this.commodityDAO.update(vo);
	}

	public boolean delete(Integer[] ids) {
		return this.commodityDAO.delete(ids);
	}
	


	public Map<Integer, Integer> getCommodityIdPictureCountMap() {
		PictureDAO pictureDAO = new PictureDAO();
		return pictureDAO.getCommodityIdPictureCountMap();
	}

	public Integer getCommodityIdPictureCount(Integer commodityId) {
		PictureDAO pictureDAO = new PictureDAO();
		return pictureDAO.getCommodityIdPictureCount(commodityId);
	}
	
	public CommodityDisplayVO getCommodityDisplayVO (CommodityVO commodityVO) {
		CommodityDisplayVO commodityWithPicCountVO = new CommodityDisplayVO();
		if (commodityVO!=null) {
			Tools.copyBeanProperties(commodityVO, commodityWithPicCountVO);
			commodityWithPicCountVO.setPictureCount(getCommodityIdPictureCount(commodityVO.getCommodityId()));
		}
		return commodityWithPicCountVO;
	}
	
	
	

	
//	public List<CommodityDisplayVO> getCommodityDisplayVOList (String commodityType, Collection<CommodityVO> commodityList) {
//		Map<Integer, Integer> pictureCountMap = getCommodityIdPictureCountMap();
//		List<CommodityAttrVO> commodityAttrList = userConfigService.getCommodityAttrMap().get(commodityType);
//		List<CommodityDisplayVO> newList = new ArrayList<>();
//		for (CommodityVO commodityVO : commodityList) {
//			CommodityDisplayVO commodityDisplayVO = new CommodityDisplayVO();
//			Tools.copyBeanProperties(commodityVO, commodityDisplayVO);
//			//pictureCount
//			Integer count = pictureCountMap.get(commodityDisplayVO.getCommodityId());
//			commodityDisplayVO.setPictureCount(count==null ? 0:count);
//			//commodityAttr
//			Map<String, String> commodityAttrValueMap = new LinkedHashMap<>();
//			List<CommodityAttrMappingVO> commodityAttrMappingList = commodityAttrMappingDAO.getByCommodityVO(commodityVO);
//			for (CommodityAttrVO commodityAttrVO : commodityAttrList) {
//				commodityAttrValueMap.put(commodityAttrVO.getCommodityAttr());
//			}
//			commodityDisplayVO.setCommodityAttrValueMap(commodityAttrValueMap);
//			newList.add(commodityDisplayVO);
//		}
//		return newList;
//	}
	
	
	
}

