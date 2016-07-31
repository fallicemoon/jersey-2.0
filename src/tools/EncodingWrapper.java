package tools;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodingWrapper extends HttpServletRequestWrapper {
	private String encoding;

	public EncodingWrapper(HttpServletRequest request, String encoding) {
		super(request);
		this.encoding = encoding;
	}

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
