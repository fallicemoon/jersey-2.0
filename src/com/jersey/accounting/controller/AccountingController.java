package com.jersey.accounting.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jersey.accounting.model.AccountingService;
import com.jersey.purchaseCase.model.PurchaseCaseVO;
import com.jersey.sellCase.model.SellCaseService;
import com.jersey.sellCase.model.SellCaseWithBenefitVO;

@Controller
@RequestMapping(value="/accounting")
public class AccountingController {
	
	@Autowired
	private AccountingService accountingService;

	private final static String REDIRECT_DATE_PICKER = "redirect:datePicker";
	private final static String DATE_PICKER = "accounting/datePicker";
	private final static String ACCOUNTING = "accounting/accounting";
	
	@Autowired
	private SellCaseService sellCaseService;

	@RequestMapping(value = "/datePicker", method = RequestMethod.GET)
	public String toDatePicker(Map<String, Object> map) {
		Calendar now = Calendar.getInstance();
		Date end = now.getTime();
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		Date start = now.getTime();
		
		//預設值為月初到今天
		map.put("start", accountingService.parseDateToString(start));
		map.put("end", accountingService.parseDateToString(end));
		return DATE_PICKER;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String accounting(Map<String, Object> map, @RequestParam(value = "start", required = true) String startString,
			@RequestParam(value = "end", required = true) String endString) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

		Date start = new Date();
		Date end = new Date();
		try {
			start = accountingService.parseStringToDate(startString);
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

	
	//for AOP 不要出錯
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String create(PurchaseCaseVO vo, Map<String, Object> map) {
		return null;
	}

	// for AOP 不要出錯
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") Integer id, @ModelAttribute("purchaseCase") PurchaseCaseVO vo,
			Map<String, Object> map) {
		return null;
	}

}
