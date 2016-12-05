package com.jersey.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jersey.systemParam.model.SystemParamDAO;
import com.jersey.systemParam.model.SystemParamVO;
import com.jersey.tools.JerseyEnum.PrimaryKey;

public class PrimaryKeyGenerator {
	
	@Autowired
	private SystemParamDAO systemParamDAO;
	
	//公開對外的pool
	private static Map<PrimaryKey, PrimaryKeyGenerator> generatorMap = new HashMap<>();
	public static PrimaryKeyGenerator getPrimaryKeyGenerator (PrimaryKey primaryKey) {
		if (generatorMap.get(primaryKey)==null) {
			generatorMap.put(primaryKey, new PrimaryKeyGenerator(primaryKey));
		} 
		return generatorMap.get(primaryKey);
	} 
	
	private static DateFormat DATE_FORMAT =  new SimpleDateFormat("yyyyMMdd");
	
	//private Constructor
	private PrimaryKey primaryKey;
	private Integer currentValue;
	private Integer maxValue;
	
	private PrimaryKeyGenerator (PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	//Generator的相關方法
	public synchronized String getNextKey () {
		String prefix = primaryKey.getPrefix();
		String date = DATE_FORMAT.format(Calendar.getInstance().getTime());
		return prefix + date + getNextValue();
	}
	
	private String getNextValue () {
		if (currentValue>=maxValue) {
			getNextValueFromDB();
		}
		//生成下個流水號
		Integer prefixZeroLength = primaryKey.getLength()-currentValue.toString().length();
		return  String.format("%0"+ prefixZeroLength +"d", currentValue.toString());
	}
	
	private void getNextValueFromDB () {
		SystemParamVO vo = systemParamDAO.getOne(primaryKey.toString());
		vo.getValue();
	}


}
