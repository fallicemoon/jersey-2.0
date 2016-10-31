package com.jersey.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.jersey.store.model.StoreService;
import com.jersey.store.model.StoreVO;

/**
 * 將前端頁面的storeId轉換成storeVO
 * @author fallicemoon
 *
 */
public class StoreConverter implements Converter<String, StoreVO> {

	@Autowired
	private StoreService storeService;
	
	@Override
	public StoreVO convert(String id) {
		Integer storeId = Integer.valueOf(id);
		return storeService.getOne(Integer.valueOf(storeId));
	}

}
