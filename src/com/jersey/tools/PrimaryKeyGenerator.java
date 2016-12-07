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
		if (primaryKey==null) {
			throw new NullPointerException();
		}
		if (generatorMap.get(primaryKey)==null) {
			generatorMap.put(primaryKey, new PrimaryKeyGenerator(primaryKey));
		} 
		return generatorMap.get(primaryKey);
	}
	
	public static PrimaryKeyGenerator getPrimaryKeyGenerator (Class<? extends AbstractVo> type) {
		return getPrimaryKeyGenerator(PrimaryKey.findByType(type));
	}
	
	private static DateFormat DATE_FORMAT =  new SimpleDateFormat("yyyyMMdd");
	
	//private Constructor
	private final PrimaryKey primaryKey;
	private final Integer dbMaxValue;
	private Integer currentValue;
	private Integer maxValue;
	private static final Integer POOL_SIZE = 50;
	
	private PrimaryKeyGenerator (PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
		this.dbMaxValue = Integer.valueOf(String.format("%0"+ primaryKey.getLength() +"d", ""));
		getNextValueFromDB();
	}
	
	//Generator的相關方法
	/**
	 * 取得PrimaryKey
	 * @return
	 */
	public synchronized String getNextPrimaryKey () {
		String prefix = primaryKey.getPrefix();
		String date = DATE_FORMAT.format(Calendar.getInstance().getTime());
		return prefix + date + getNextValue();
	}
	
	/**
	 * 生成下個流水號
	 * @return
	 */
	private String getNextValue () {
		if (currentValue > maxValue) {
			getNextValueFromDB();
		}
		//生成下個流水號
		Integer prefixZeroLength = primaryKey.getLength()-currentValue.toString().length();
		String nextValue = String.format("%0"+ prefixZeroLength +"d", currentValue.toString());
		currentValue++;
		return nextValue;
	}
	
	/**
	 * 從DB取號並更新DB目前流水號
	 */
	private void getNextValueFromDB () {
		if (currentValue+POOL_SIZE>=dbMaxValue) {
			System.out.println("start reset DB...");
			resetDB();
		}
		SystemParamVO vo = systemParamDAO.getPrimaryKeyValueAndUpdate(primaryKey, POOL_SIZE);
		currentValue = Integer.parseInt(vo.getValue())-POOL_SIZE;
		maxValue = Integer.parseInt(vo.getValue())-1;
	}
	
	/**
	 * 超過的話reset DB的流水號
	 */
	private void resetDB () {
		systemParamDAO.resetPrimaryKey(primaryKey);
	}


}
