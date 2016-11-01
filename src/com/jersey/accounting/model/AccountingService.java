package com.jersey.accounting.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class AccountingService {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	
	public Date parseStringToDate (String string) throws ParseException {
		return sdf.parse(string.replaceAll("AM", "上午").replaceAll("PM", "下午"));
	}
	
	public String parseDateToString (Date date) {
		String string = sdf.format(date);
		return string.replaceAll("上午", "AM").replaceAll("下午", "PM");
	} 

}
