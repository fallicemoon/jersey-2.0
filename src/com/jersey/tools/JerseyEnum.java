package com.jersey.tools;

import java.util.ArrayList;
import java.util.List;

public class JerseyEnum {

	public enum StoreType {
		store, shippingCompany;
	}
	
	public enum CommodityAttrAuthority {
		all, customer, customerHidden, admin, adminHidden;
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
