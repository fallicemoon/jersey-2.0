package member.controller;

import java.io.IOException;
import java.util.LinkedHashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import member.model.MemberService;

public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String forwardUrl = "/WEB-INF/pages";
	private final String forwardLoginUrl = forwardUrl + "/login.jsp";
	private final String forwardIndexUrl = forwardUrl + "/index.jsp";
	private final MemberService memberService = new MemberService();

	private final String login = "login";
	private final String logout = "logout";
	private final String ok = "ok";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		String loginResult = (String) session.getAttribute(login);
		LinkedHashSet<String> errors = new LinkedHashSet<>();

		if (StringUtils.isEmpty(action)) {
			if (ok.equals(loginResult)) {
				request.getRequestDispatcher(forwardIndexUrl).forward(request, response);
			} else {
				request.getRequestDispatcher(forwardLoginUrl).forward(request, response);
			}
		} else if (login.equals(action)) {
			if (login(request)) {
				request.getRequestDispatcher(forwardIndexUrl).forward(request, response);
			} else {
				errors.add("廢物, 帳號密碼錯了");
				request.setAttribute("errors", errors);
				request.getRequestDispatcher(forwardLoginUrl).forward(request, response);
			}
		} else if (logout.equals(action)) {
			session.removeAttribute("login");
			request.getRequestDispatcher(forwardLoginUrl).forward(request, response);
		}

	}

	private boolean login(HttpServletRequest request) throws ServletException, IOException {
		boolean login = false;
		HttpSession session = request.getSession();
		String _user = getServletContext().getInitParameter("user");
		String _password = getServletContext().getInitParameter("password");
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		if (user.equals(_user) && password.equals(_password)) {
			session.setAttribute("login", ok);
			login = true;
		}
		return login;
	}	
	
}
