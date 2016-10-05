package com.jersey.commodity.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.picture.model.PictureDAO;
import com.jersey.tools.Tools;
import com.jersey.userConfig.model.CommodityAttrVO;
import com.jersey.userConfig.model.CommodityTypeVO;
import com.jersey.userConfig.model.UserConfigService;
import com.jersey.userConfig.model.UserConfigVO;

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
		return new ArrayList<>(userConfigService.getUserSession().getCommodityTypeAttrMap().keySet());
	}
	
	/**
	 * 取得某商品種類的所有商品屬性
	 * @param commodityTypeId
	 * @return
	 */
	public List<CommodityAttrVO> getCommodityAttrByCommodityTypeId (String commodityTypeId) {
		CommodityTypeVO commodityTypeVO = new CommodityTypeVO();
		return userConfigService.getUserSession().getCommodityTypeAttrMap().get(commodityTypeVO);
	}

	//取得所有商品
	public List<CommodityDisplayVO> getAll(CommodityTypeVO commodityTypeVO, Integer page) {
		List<CommodityVO> oldList = commodityDAO.getAll(userConfigService.getUserSession().getUserConfigVO().getAuthority(), commodityTypeVO, userConfigService.getUserSession().getUserConfigVO().getCommodityPageSize(), page);
		List<CommodityDisplayVO> newList = new ArrayList<>();
		for (CommodityVO commodityVO : oldList) {
			newList.add(getCommodityDisplayVO(commodityVO));
		}
		return newList;
	}
	
	//取得總分頁數
	public Long getPages (CommodityTypeVO commodityTypeVO) {
		UserConfigVO userConfigVO = userConfigService.getUserSession().getUserConfigVO();
		Long pages = getTotalCount(commodityTypeVO)/userConfigVO.getCommodityPageSize();
		if (getTotalCount(commodityTypeVO)%userConfigService.getUserSession().getUserConfigVO().getCommodityPageSize()!=0) {
			pages++;
		}
		return pages;
	}
	
	//取得資料總筆數
	public Long getTotalCount (CommodityTypeVO commodityTypeVO) {
		return commodityDAO.getTotalCount(userConfigService.getUserSession().getUserConfigVO().getAuthority(), commodityTypeVO);
	}

	public CommodityVO getOne(Integer commodityId) {
		return this.commodityDAO.getOne(userConfigService.getUserSession().getUserConfigVO().getAuthority(), commodityId);
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
		CommodityDisplayVO commodityDisplayVO = new CommodityDisplayVO();
		if (commodityVO!=null) {
			Tools.copyBeanProperties(commodityVO, commodityDisplayVO);
			commodityDisplayVO.setPictureCount(getCommodityIdPictureCount(commodityVO.getCommodityId()));
			//開始處理屬性
			List<CommodityAttrValueVO> list = new ArrayList<>();
			Set<CommodityAttrMappingVO> set = commodityVO.getCommodityAttrMappings();
			for (CommodityAttrMappingVO commodityAttrMappingVO : set) {
				CommodityAttrValueVO commodityAttrValueVO = new CommodityAttrValueVO();
				commodityAttrValueVO.setCommodityAttr(commodityAttrMappingVO.getCommodityAttrVO().getCommodityAttr());
				commodityAttrValueVO.setCommodityAttrValue(commodityAttrMappingVO.getCommodityAttrValue());
				commodityAttrValueVO.setCommodityAttrAuthority(commodityAttrMappingVO.getCommodityAttrVO().getCommodityAttrAuthority());
				list.add(commodityAttrValueVO);
			}
			commodityDisplayVO.setCommodityAttrValueList(list);
		}
		return commodityDisplayVO;
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

