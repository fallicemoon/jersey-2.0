package com.jersey.tools;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class JerseyWrapper extends HttpServletRequestWrapper {
	private String encoding;

	public JerseyWrapper(HttpServletRequest request, String encoding) {
		super(request);
		this.encoding = encoding;
	}
	
	@Override
	public String[] getParameterValues (String name) {
		String[] values = getRequest().getParameterValues(name);
		if (values!=null) {
			for (int i = 0; i < values.length; i++) {
				try {
					values[i] = new String(values[i].getBytes("ISO-8859-1"), this.encoding);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return values;
	}

	@Override
	public String getParameter(String name) {
		String value = getRequest().getParameter(name);
		try {
			if (value != null)
				value = new String(value.getBytes("ISO-8859-1"), this.encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}
	

}
