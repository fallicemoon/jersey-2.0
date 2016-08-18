package com.jersey.commodity.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.picture.model.PictureDAO;
import com.jersey.tools.Tools;
import com.jersey.userConfig.model.UserConfigService;
import com.jersey.tools.JerseyEnum.Authority;

@Service
public class CommodityService {

	
	@Autowired
	private CommodityDAO commodityDAO;
	@Autowired
	private UserConfigService userConfigService;


	public List<CommodityDisplay> getAll(Integer pageSize, Integer page, Authority authority) {
		return getCommodityDisplayVOList(commodityDAO.getAll(pageSize, page), authority);
	}
	
	public Long getPages () {
		Long pages = getTotalCount()/userConfigService.getCommodityPageSize();
		if (getTotalCount()%userConfigService.getCommodityPageSize()!=0) {
			pages++;
		}
		return pages;
	} 
	
	public Long getTotalCount () {
		return commodityDAO.getTotalCount();
	} 

	public CommodityVO getOne(Integer id) {
		return this.commodityDAO.getOne(id);
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
	
	public CommodityDisplay getCommodityDisplayVO (CommodityVO commodityVO) {
		CommodityDisplay commodityWithPicCountVO = new CommodityDisplay();
		if (commodityVO!=null) {
			Tools.copyBeanProperties(commodityVO, commodityWithPicCountVO);
			commodityWithPicCountVO.setPictureCount(getCommodityIdPictureCount(commodityVO.getCommodityId()));
		}
		return commodityWithPicCountVO;
	}
	
	public List<CommodityDisplay> getCommodityDisplayVOList (Collection<CommodityVO> commodityList, Authority authority) {
		Map<Integer, Integer> pictureCountMap = getCommodityIdPictureCountMap();
		List<CommodityDisplay> newList = new ArrayList<>();
		for (CommodityVO commodityVO : commodityList) {
			CommodityDisplay commodityDisplayVO = new CommodityDisplay();
			Tools.copyBeanProperties(commodityVO, commodityDisplayVO);
			//pictureCount
			Integer count = pictureCountMap.get(commodityDisplayVO.getCommodityId());
			commodityDisplayVO.setPictureCount(count==null ? 0:count);
			//commodityAttrValue
			//TODO cache
			commodityDisplayVO.setCommodityAttrValueList(commodityDAO.getCommodityAttrValue(commodityDisplayVO.getCommodityId(), authority));
			newList.add(commodityDisplayVO);
		}
		return newList;
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

