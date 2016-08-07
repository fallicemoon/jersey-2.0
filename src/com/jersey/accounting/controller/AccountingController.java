package com.jersey.accounting.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jersey.sellCase.model.SellCaseService;
import com.jersey.sellCase.model.SellCaseWithBenefitVO;

@Controller
@RequestMapping(value="/accounting")
public class AccountingController {

	private final static String REDIRECT_DATE_PICKER = "redirect:datePicker";
	private final static String DATE_PICKER = "accounting/datePicker";
	private final static String ACCOUNTING = "accounting/accounting";
	
	@Autowired
	private SellCaseService sellCaseService;

	@RequestMapping(value = "/datePicker", method = RequestMethod.GET)
	public String toDatePicker(Map<String, Object> map) {
		System.out.println("123");
		return DATE_PICKER;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String accounting(Map<String, Object> map, @RequestParam(value = "start", required = true) String startString,
			@RequestParam(value = "end", required = true) String endString) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

		Date start = new Date();
		Date end = new Date();
		try {
			start = sdf.parse(startString.replaceAll("AM", "上午").replaceAll("PM", "下午"));
		} catch (ParseException e) {
			return REDIRECT_DATE_PICKER;
		}
		try {
			end = sdf.parse(endString.replaceAll("AM", "上午").replaceAll("PM", "下午"));
		} catch (ParseException e) {
			return REDIRECT_DATE_PICKER;
		}

		List<SellCaseWithBenefitVO> list = sellCaseService.getBetweenCloseTime(start, end);
		map.put("start", sdf.format(start));
		map.put("end", sdf.format(end));
		map.put("sellCaseList", list);
		map.put("totalBenefit", sellCaseService.getTotalBenefit(list));

		return ACCOUNTING;
	}

}
