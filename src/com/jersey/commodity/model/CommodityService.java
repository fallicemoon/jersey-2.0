package com.jersey.commodity.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.picture.model.PictureDAO;
import com.jersey.tools.JerseyEnum.Authority;
import com.jersey.tools.JerseyEnum.CommodityAttrAuthority;
import com.jersey.tools.JerseyEnum.PrimaryKey;
import com.jersey.tools.JerseyTools;
import com.jersey.tools.PrimaryKeyGeneratorPool;
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
	private PictureDAO pictureDAO;
	@Autowired
	private UserSession userSession;
	@Autowired
	private PrimaryKeyGeneratorPool primaryKeyGeneratorPool;
	
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

	//取得所有商品(商品屬性調整時用)
	public List<CommodityVO> getAll(CommodityTypeVO commodityTypeVO) {
		return commodityDAO.getAll(userSession.getUserConfigVO().getAuthority(), commodityTypeVO);
	}
	
	//按照分頁取得所有商品
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

	public CommodityVO getOne(String commodityId) {
		CommodityVO commodityVO = this.commodityDAO.getOne(commodityId);
		if (userSession.getUserConfigVO().getAuthority()==Authority.CUSTOMER && commodityVO.getAuthority()!=Authority.CUSTOMER) {
			commodityVO = null;
		}
		return commodityVO;
		
	}

	public CommodityVO create(CommodityVO vo) {
		//如果vo沒有塞商品屬性值,就新增空白的商品屬性值
		if (vo.getCommodityAttrMappings()==null || vo.getCommodityAttrMappings().size()==0) {
			List<CommodityAttrVO> commodityAttrVOs = userSession.getCommodityTypeAttrMap().get(vo.getCommodityTypeVO());
			SortedSet<CommodityAttrMappingVO> commodityAttrMappings = new TreeSet<>();
			for (CommodityAttrVO commodityAttrVO : commodityAttrVOs) {
				CommodityAttrMappingVO commodityAttrMappingVO = new CommodityAttrMappingVO();
				commodityAttrMappingVO.setId(primaryKeyGeneratorPool.getPrimaryKeyGenerator(PrimaryKey.COMMODITY_ATTR_MAPPING_ID).getNextPrimaryKey());
				commodityAttrMappingVO.setCommodityVO(vo);
				commodityAttrMappingVO.setCommodityAttrVO(commodityAttrVO);
				commodityAttrMappings.add(commodityAttrMappingVO);
			}
			vo.setCommodityAttrMappings(commodityAttrMappings);
		}
		return this.commodityDAO.create(vo);
	}

	public CommodityVO update(CommodityVO vo) {
		return this.commodityDAO.update(vo);
	}

	public boolean delete(Integer[] ids) {
		return this.commodityDAO.delete(ids);
	}

	public Integer getCommodityIdPictureCount(String commodityId) {
		return pictureDAO.getCommodityIdPictureCount(commodityId);
	}
	
	/**
	 * 取得此使用者在此商品種類下可見的屬性
	 * @param commodityTypeVO
	 * @return
	 */
	public List<CommodityAttrVO> getVisibleCommodityAttr (CommodityTypeVO commodityTypeVO) {
		//把adminHidden的拿掉，但不能影響到session裡面的map, 所以要用new的
		List<CommodityAttrVO> list = new ArrayList<>(userSession.getCommodityTypeAttrMap().get(commodityTypeVO));
		Iterator<CommodityAttrVO> commodityAttrVOIterator = list.iterator();
		while (commodityAttrVOIterator.hasNext()) {
			CommodityAttrVO commodityAttrVO = commodityAttrVOIterator.next();
			if(commodityAttrVO.getCommodityAttrAuthority()==CommodityAttrAuthority.ADMIN_HIDDEN){
				commodityAttrVOIterator.remove();
			}
		}
		return list;
	}
	
	public CommodityDisplayVO getCommodityDisplayVO (CommodityVO commodityVO) {
		CommodityDisplayVO commodityDisplayVO = null;
		if (commodityVO!=null) {
			commodityDisplayVO = new CommodityDisplayVO();
			JerseyTools.copyAbstractVoProperties(commodityVO, commodityDisplayVO);
			commodityDisplayVO.setPictureCount(getCommodityIdPictureCount(commodityVO.getId()));
			//刪掉此權限不能看的屬性(adminHidden一定看不到)
			Iterator<CommodityAttrMappingVO> iterator = commodityVO.getCommodityAttrMappings().iterator();
			while (iterator.hasNext()) {
				CommodityAttrMappingVO commodityAttrMappingVO = (CommodityAttrMappingVO) iterator.next();
				List<CommodityAttrAuthority> authorities = CommodityAttrAuthority.getAllowByAuthority(userSession.getUserConfigVO().getAuthority());
				CommodityAttrAuthority commodityAttrAuthority = commodityAttrMappingVO.getCommodityAttrVO().getCommodityAttrAuthority();
				if (!authorities.contains(commodityAttrAuthority) || commodityAttrAuthority==CommodityAttrAuthority.ADMIN_HIDDEN) {
					iterator.remove();
				}
			}

		}
		return commodityDisplayVO;
	}
	
	public List<CommodityDisplayVO> getCommodityDisplayVO (Collection<CommodityVO> list) {
		List<CommodityDisplayVO> newList = new ArrayList<>();
		for (CommodityVO commodityDisplayVO : list) {
			newList.add(getCommodityDisplayVO(commodityDisplayVO));
		}
		return newList;
	} 
	
	public CommodityAttrMappingVO getCommodityAttrMappingVO(String commodityAttrMappingId) {
		return commodityAttrMappingDAO.getOne(commodityAttrMappingId);
	}
	
	public List<CommodityAttrMappingVO> getCommodityAttrMappingVOList(CommodityVO commodityVO) {
		return commodityAttrMappingDAO.getByCommodityVO(commodityVO);
	}
	
	
}

