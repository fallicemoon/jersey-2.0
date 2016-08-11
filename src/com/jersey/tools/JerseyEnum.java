package com.jersey.tools;

public class JerseyEnum {

	public enum StoreType {
		store, shippingCompany;
	}
	
	public enum commodityAttrAuthority {
		ALL, CUSTOMER, CUSTOMER_HIDDEN, ADMIN, ADMIN_HIDDEN;
	}

	public enum UserConfig {
		commodityPageSize, purchaseCasePageSize, sellCasePageSize, storePageSize, commodityAttr;

	}
}
