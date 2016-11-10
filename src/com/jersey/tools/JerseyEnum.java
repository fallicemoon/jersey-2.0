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
	
	public enum UserConfigType {
		SYSTEM_PARAM
	}

	public enum UserConfig {
		COMMODITY_PAGE_SIZE(UserConfigType.SYSTEM_PARAM, Integer.class), 
		PURCHASE_CASE_PAGE_SIZE(UserConfigType.SYSTEM_PARAM, Integer.class), 
		SELL_CASE_PAGE_SIZE(UserConfigType.SYSTEM_PARAM, Integer.class), 
		STORE_PAGE_SIZE(UserConfigType.SYSTEM_PARAM, Integer.class);
		
		private UserConfigType userConfigType;
		private Class<?> dataType;
		private UserConfig (UserConfigType userConfigType, Class<?> dataType) {
			this.userConfigType = userConfigType;
			this.dataType = dataType;
		}
		
		public List<UserConfig> getAllByUserConfigType (UserConfigType userConfigType) {
			List<UserConfig> list = new ArrayList<>();
			for (UserConfig userConfig : UserConfig.values()) {
				if (userConfig.userConfigType==userConfigType) {
					list.add(userConfig);
				}
			}
			return list;
		}
		
		public Class<?> getDataType() {
			return dataType;
		}
	}
	
	
	
	
	
}
