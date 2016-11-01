package com.jersey.aop;

import java.util.Date;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.jersey.tools.AbstractVo;
import com.jersey.tools.Tools;

@Component
@Aspect
public class ParseVoAspect {
	
	@Before(value = "execution(public String *.*Controller.create(AbstractVo, Map)) && args(abstractVo)")
	public void beforeCreate (AbstractVo abstractVo) {
		Tools.parseVoNullValue(abstractVo);
		abstractVo.setCreateTime(new Date());
		abstractVo.setLastModifyTime(new Date());
//		if (abstractVo instanceof PurchaseCaseVO) {
//			
//		} else if (abstractVo instanceof SellCaseVO) {
//			
//		}
	}
	
//	@Before(value = "execution(public String *.*Controller.update(AbstractVo, Map)) && args(abstractVo)")
//	public void beforeUpdate (AbstractVo abstractVo) {
//		Tools.parseVoNullValue(abstractVo);
//		abstractVo.setLastModifyTime(new Date());
////		if (abstractVo instanceof PurchaseCaseVO) {
////			
////		} else if (abstractVo instanceof SellCaseVO) {
////			
////		}
//	}
	

}
