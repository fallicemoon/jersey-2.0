package com.jersey.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		COMMODITY_ID("CY", 6),
		PURCHASE_CASE_ID("PC", 6),
		SELL_CASE_ID("SC", 6),
		STORE_ID("SE", 6),
		PICTURE_ID("PE", 6),
		USER_CONFIG_ID("UC", 6),
		COMMODITY_ATTR_ID("CA", 6),
		COMMODITY_ATTR_MAPPING_ID("CM", 6),
		COMMODITY_TYPE_ID("CT", 6);
		
		private String prefix;
		private Integer length;
		private PrimaryKey (String prefix, Integer length) {
			this.prefix = prefix;
			this.length = length;
		}
		public String getPrefix() {
			return prefix;
		}
		public Integer getLength() {
			return length;
		}
	}
	
	
	
}
