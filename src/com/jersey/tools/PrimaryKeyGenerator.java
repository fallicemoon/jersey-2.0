package com.jersey.tools;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.LockMode;

import com.jersey.tools.JerseyEnum.PrimaryKey;

public class PrimaryKeyGenerator {
	
	//公開對外的pool
	private static Map<PrimaryKey, PrimaryKeyGenerator> generatorMap = new HashMap<>();
	public static PrimaryKeyGenerator getPrimaryKeyGenerator (PrimaryKey primaryKey) {
		if (generatorMap.get(primaryKey)==null) {
			generatorMap.put(primaryKey, new PrimaryKeyGenerator(primaryKey));
		} 
		return generatorMap.get(primaryKey);
	} 
	
	//private Constructor
	private PrimaryKey primaryKey;
	private Integer currentValue;
	private Integer maxValue;
	private PrimaryKeyGenerator (PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	//Generator的相關方法
	public String getNextKey () {
		
	}
	
	


}
