package com.jersey.tools;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

public class Tools {

	private static final String serialVersionUID = "serialVersionUID";

	
	/**
	 * 複製bean裡面的同名屬性(不會複製serialVersionUID)
	 * @param old
	 * @param target
	 */
	public static void copyBeanProperties(Object old, Object target, String... notCopyFields) {
		Field[] oldFields = old.getClass().getDeclaredFields();
		Set<String> notCopyFieldSet = new HashSet<>();
		Collections.addAll(notCopyFieldSet, notCopyFields);
		notCopyFieldSet.add(serialVersionUID);
		
		for (Field field : oldFields) {
			if (notCopyFieldSet.contains(field.getName()))
				continue;
			field.setAccessible(true);
			try {
				Object value = field.get(old);
				field.set(target, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				continue;
			}
		}

	}
	
	public static JSONObject getSuccessJson () {
		JSONObject json = new JSONObject();
		json.put("result", "success");
		return json;
	}
	
	
	
	
	
	
	
}
