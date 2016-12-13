package com.jersey.tools;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jersey.systemParam.model.SystemParamDAO;
import com.jersey.tools.JerseyEnum.PrimaryKey;

@Component
public class PrimaryKeyGeneratorPool {
	
	@Autowired
	private SystemParamDAO systemParamDAO;

	// 公開對外的pool
	private Map<PrimaryKey, PrimaryKeyGenerator> generatorMap = new HashMap<>();

	public PrimaryKeyGenerator getPrimaryKeyGenerator(PrimaryKey primaryKey) {
		if (primaryKey == null) {
			throw new NullPointerException();
		}
		if (generatorMap.get(primaryKey) == null) {
			generatorMap.put(primaryKey, new PrimaryKeyGenerator(primaryKey, systemParamDAO));
		}
		return generatorMap.get(primaryKey);
	}

	public PrimaryKeyGenerator getPrimaryKeyGenerator(Class<? extends AbstractVo> type) {
		return getPrimaryKeyGenerator(PrimaryKey.findByType(type));
	}

	private PrimaryKeyGeneratorPool() {

	}

}
