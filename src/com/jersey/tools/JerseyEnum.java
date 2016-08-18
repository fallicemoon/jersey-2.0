package com.jersey.tools;

import java.util.ArrayList;
import java.util.List;

public class JerseyEnum {

	public enum StoreType {
		store, shippingCompany;
	}
	
	public enum Authority {
		admin, customer;
	}
	
	public enum CommodityAttrStatus {
		show, hidden;
	}
	
	public enum CommodityAttrAuthority {
		admin(Authority.admin, CommodityAttrStatus.show, "管理員"), 
		adminHidden(Authority.admin, CommodityAttrStatus.hidden,"管理員(隱藏)"), 
		customer(Authority.customer, CommodityAttrStatus.show, "顧客");
		
		private Authority authority;
		private CommodityAttrStatus commodityAttrStatus;
		private String showName;
		private CommodityAttrAuthority (Authority authority, CommodityAttrStatus commodityAttrStatus, String showName) {
			this.authority = authority;
			this.commodityAttrStatus = commodityAttrStatus;
			this.showName = showName;
		}
		
		public static List<CommodityAttrAuthority> getByAuthority (Authority authority) {
			List<CommodityAttrAuthority> list = new ArrayList<>();
			for (CommodityAttrAuthority commodityAttrAuthority : CommodityAttrAuthority.values()) {
				if (commodityAttrAuthority.authority == authority) {
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
		systemParam
	}

	public enum UserConfig {
		commodityPageSize(UserConfigType.systemParam, Integer.class), 
		purchaseCasePageSize(UserConfigType.systemParam, Integer.class), 
		sellCasePageSize(UserConfigType.systemParam, Integer.class), 
		storePageSize(UserConfigType.systemParam, Integer.class);
		
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
