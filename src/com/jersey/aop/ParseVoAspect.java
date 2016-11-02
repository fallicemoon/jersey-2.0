package com.jersey.aop;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.jersey.tools.AbstractVo;
import com.jersey.tools.Tools;

@Component
@Aspect
public class ParseVoAspect {
	
	@Before(value = "execution(public * *..*Service.create(..))")
	public void beforeCreate (JoinPoint joinPoint) {
		System.out.println("start aop beforeCreate...");
		Object[] args = joinPoint.getArgs();
		AbstractVo abstractVo = null;
		for (Object object : args) {
			if (object instanceof AbstractVo) {
				abstractVo = (AbstractVo)object;
			}
		}
		Tools.parseVoNullValue(abstractVo);
		abstractVo.setCreateTime(new Date());
		abstractVo.setLastModifyTime(new Date());

	}
	
	@Before(value = "execution(public * *..*Service.update(..))")
	public void beforeUpdate (JoinPoint joinPoint) {
		System.out.println("start aop beforeUpdate...");
		Object[] args = joinPoint.getArgs();
		AbstractVo abstractVo = null;
		for (Object object : args) {
			if (object instanceof AbstractVo) {
				abstractVo = (AbstractVo)object;
			}
		}
		Tools.parseVoNullValue(abstractVo);
		abstractVo.setLastModifyTime(new Date());
	}
	

}
