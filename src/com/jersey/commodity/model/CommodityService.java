package com.jersey.commodity.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.picture.model.PictureDAO;
import com.jersey.tools.JerseyEnum.Authority;
import com.jersey.tools.JerseyEnum.CommodityAttrAuthority;
import com.jersey.tools.Tools;
import com.jersey.userConfig.model.CommodityAttrVO;
import com.jersey.userConfig.model.CommodityTypeVO;
import com.jersey.userConfig.model.UserConfigVO;
import com.jersey.userConfig.model.UserSession;

@Service
public class CommodityService {

	
	@Autowired
	private CommodityDAO commodityDAO;
	@Autowired
	private CommodityAttrMappingDAO commodityAttrMappingDAO;
	@Autowired
	private UserSession userSession;
	
	/**
	 * 取得所有商品種類
	 * @return 此List為LinkedHashSet轉成, 順序就是從DB中撈出來的順序
	 */
	public List<CommodityTypeVO> getCommodityType () {
		return new ArrayList<>(userSession.getCommodityTypeAttrMap().keySet());
	}
	
	/**
	 * 取得某商品種類的所有商品屬性
	 * @param commodityTypeId
	 * @return
	 */
	public List<CommodityAttrVO> getCommodityAttrByCommodityTypeId (String commodityTypeId) {
		CommodityTypeVO commodityTypeVO = new CommodityTypeVO();
		return userSession.getCommodityTypeAttrMap().get(commodityTypeVO);
	}

	//取得所有商品
	public List<CommodityDisplayVO> getAll(CommodityTypeVO commodityTypeVO, Integer page) {
		List<CommodityVO> oldList = commodityDAO.getAll(userSession.getUserConfigVO().getAuthority(), commodityTypeVO, userSession.getUserConfigVO().getCommodityPageSize(), page);
		//刪掉不屬於此權限的屬性, 塞入圖片張數
		List<CommodityDisplayVO> newList = new ArrayList<>();
		for (CommodityVO commodityVO : oldList) {
			newList.add(getCommodityDisplayVO(commodityVO));
		}
		return newList;
	}
	
	//取得總分頁數
	public Long getPages (CommodityTypeVO commodityTypeVO) {
		UserConfigVO userConfigVO = userSession.getUserConfigVO();
		Long pages = getTotalCount(commodityTypeVO)/userConfigVO.getCommodityPageSize();
		if (getTotalCount(commodityTypeVO)%userSession.getUserConfigVO().getCommodityPageSize()!=0) {
			pages++;
		}
		return pages;
	}
	
	//取得資料總筆數
	public Long getTotalCount (CommodityTypeVO commodityTypeVO) {
		return commodityDAO.getTotalCount(userSession.getUserConfigVO().getAuthority(), commodityTypeVO);
	}

	public CommodityVO getOne(Integer commodityId) {
		return this.commodityDAO.getOne(userSession.getUserConfigVO().getAuthority(), commodityId);
	}

	public CommodityVO create(CommodityVO vo) {
		//新增空白的商品屬性值
		List<CommodityAttrVO> commodityAttrVOs = userSession.getCommodityTypeAttrMap().get(vo.getCommodityTypeVO());
		Set<CommodityAttrMappingVO> commodityAttrMappings = new HashSet<>();
		for (CommodityAttrVO commodityAttrVO : commodityAttrVOs) {
			CommodityAttrMappingVO commodityAttrMappingVO = new CommodityAttrMappingVO();
			commodityAttrMappingVO.setCommodityVO(vo);
			commodityAttrMappingVO.setCommodityAttrVO(commodityAttrVO);
			commodityAttrMappings.add(commodityAttrMappingVO);
		}
		vo.setCommodityAttrMappings(commodityAttrMappings);
		//預設
		vo.setAuthority(Authority.admin);
		
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
			//刪掉此權限不能看的屬性
			Iterator<CommodityAttrMappingVO> iterator = commodityVO.getCommodityAttrMappings().iterator();
			while (iterator.hasNext()) {
				CommodityAttrMappingVO commodityAttrMappingVO = (CommodityAttrMappingVO) iterator.next();
				List<CommodityAttrAuthority> authorities = CommodityAttrAuthority.getAllowByAuthority(userSession.getUserConfigVO().getAuthority());
				if (!authorities.contains(commodityAttrMappingVO.getCommodityAttrVO().getCommodityAttrAuthority())) {
					iterator.remove();
				}
			}

		}
		return commodityDisplayVO;
	}
	
	public CommodityAttrMappingVO getCommodityAttrMappingVO(Integer commodityAttrMappingId) {
		return commodityAttrMappingDAO.getOne(commodityAttrMappingId);
	}
	
	public List<CommodityAttrMappingVO> getCommodityAttrMappingVOList(CommodityVO commodityVO) {
		return commodityAttrMappingDAO.getByCommodityVO(commodityVO);
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

