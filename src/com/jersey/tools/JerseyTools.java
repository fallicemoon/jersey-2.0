package com.jersey.tools;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

public class JerseyTools {

	private static final String serialVersionUID = "serialVersionUID";

	
	/**
	 * 複製bean裡面的同名屬性(不會複製serialVersionUID)
	 * @param old
	 * @param target
	 */
	public static void copyAbstractVoProperties(AbstractVo old, AbstractVo target, String... notCopyFields) {
		Field[] oldFields = old.getClass().getDeclaredFields();
		Set<Field> oldFieldSet = new HashSet<>(Arrays.asList(oldFields));
		//把AbstractVo這個父類別的變數也加進來
		oldFieldSet.addAll(new HashSet<>(Arrays.asList(AbstractVo.class.getDeclaredFields())));
		
		Set<String> notCopyFieldSet = new HashSet<>();
		Collections.addAll(notCopyFieldSet, notCopyFields);
		notCopyFieldSet.add(serialVersionUID);
		for (Field field : oldFieldSet) {
			boolean flag = field.isAccessible();
			if (notCopyFieldSet.contains(field.getName())){
				continue;
			}
			field.setAccessible(true);
			try {
				Object value = field.get(old);
				field.set(target, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				//新物件沒有這個屬性
				continue;
			}
			field.setAccessible(flag);
		}

	}
	
	public static JSONObject getSuccessJson () {
		JSONObject json = new JSONObject();
		json.put("result", "success");
		return json;
	}
	
	public static JSONObject getFailJson (String msg) {
		JSONObject json = new JSONObject();
		json.put("result", "fail");
		json.put("msg", msg);
		return json;
	}
	
	/**
	 * spring MVC 所有checkBox的值都要call這方法
	 * @param checkboxValue
	 * @return
	 */
	public static void parseVoNullValue (Object vo) {
		Field[] voFields = vo.getClass().getDeclaredFields();
		for (Field field : voFields) {
			try {
				field.setAccessible(true);
				if (field.get(vo)==null) {
					if (field.getType()==Integer.class) {
						field.set(vo, 0);
					} else if (field.getType()==Boolean.class) {
						field.set(vo, false);
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			
		}
	}
	

	
	
	
	
}
