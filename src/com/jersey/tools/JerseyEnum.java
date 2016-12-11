package com.jersey.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jersey.commodity.model.CommodityAttrMappingVO;
import com.jersey.commodity.model.CommodityVO;
import com.jersey.picture.model.PictureVO;
import com.jersey.purchaseCase.model.PurchaseCaseVO;
import com.jersey.sellCase.model.SellCaseVO;
import com.jersey.store.model.StoreVO;
import com.jersey.systemParam.model.SystemParamVO;
import com.jersey.userConfig.model.CommodityAttrVO;
import com.jersey.userConfig.model.CommodityTypeVO;

public class JerseyEnum {

	public enum StoreType {
		STORE, SHIPPING_COMPANY;
	}
	
	public enum Authority {
		ADMIN("管理員"), CUSTOMER("顧客");
		private String showName;
		private Authority (String showName) {
			this.showName = showName;
		}
		public String getShowName() {
			return showName;
		}
	}
	
	public enum CommodityAttrStatus {
		SHOW, HIDDEN;
	}
	
	public enum CommodityAttrAuthority {
		ADMIN(CommodityAttrStatus.SHOW, "管理員", Authority.ADMIN), 
		ADMIN_HIDDEN(CommodityAttrStatus.HIDDEN,"管理員(隱藏)", Authority.ADMIN), 
		CUSTOMER(CommodityAttrStatus.SHOW, "顧客", Authority.ADMIN, Authority.CUSTOMER);
		
		private List<Authority> allowAuthority;
		private CommodityAttrStatus commodityAttrStatus;
		private String showName;
		private CommodityAttrAuthority (CommodityAttrStatus commodityAttrStatus, String showName, Authority... allowAuthority) {
			this.allowAuthority = Arrays.asList(allowAuthority);
			this.commodityAttrStatus = commodityAttrStatus;
			this.showName = showName;
		}
		
		public static List<CommodityAttrAuthority> getAllowByAuthority (Authority authority) {
			List<CommodityAttrAuthority> list = new ArrayList<>();
			for (CommodityAttrAuthority commodityAttrAuthority : CommodityAttrAuthority.values()) {
				if (commodityAttrAuthority.allowAuthority.contains(authority)) {
					list.add(commodityAttrAuthority);
				}
			}
			return list;
		}
		
		public String getShowName() {
			return showName;
		}
		
		public CommodityAttrStatus getCommodityAttrStatus() {
			return commodityAttrStatus;
		}
		
	}

	public enum UserConfig {
		COMMODITY_PAGE_SIZE(Integer.class), 
		PURCHASE_CASE_PAGE_SIZE(Integer.class), 
		SELL_CASE_PAGE_SIZE(Integer.class), 
		STORE_PAGE_SIZE(Integer.class);
		
		private Class<?> dataType;
		private UserConfig (Class<?> dataType) {
			this.dataType = dataType;
		}
		
		public Class<?> getDataType() {
			return dataType;
		}
	}
	
	public enum PrimaryKey {
		COMMODITY_ID(CommodityVO.class, "CY", 6),
		PURCHASE_CASE_ID(PurchaseCaseVO.class, "PC", 6),
		SELL_CASE_ID(SellCaseVO.class, "SC", 6),
		STORE_ID(StoreVO.class, "SE", 6),
		PICTURE_ID(PictureVO.class, "PE", 6),
		USER_CONFIG_ID(UserConfig.class, "UC", 6),
		COMMODITY_ATTR_ID(CommodityAttrVO.class, "CA", 6),
		COMMODITY_ATTR_MAPPING_ID(CommodityAttrMappingVO.class, "CM", 6),
		COMMODITY_TYPE_ID(CommodityTypeVO.class, "CT", 6),
		SYSTEM_PARAM_ID(SystemParamVO.class, "SP", 6);
		
		private Class<?> type;
		private String prefix;
		private Integer length;
		private PrimaryKey (Class<?> type, String prefix, Integer length) {
			this.type = type;
			this.prefix = prefix;
			this.length = length;
		}
		
		public Class<?> getType() {
			return type;
		}
		public String getPrefix() {
			return prefix;
		}
		public Integer getLength() {
			return length;
		}
		
		public static PrimaryKey findByType (Class<? extends AbstractVo> type) {
			for (PrimaryKey primaryKey : PrimaryKey.values()) {
				if (primaryKey.type==type) {
					return primaryKey;
				}
			}
			return null;
		}
	}
	
	
	
}
