package store.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import store.model.StoreService;
import store.model.StoreVO;
import tools.JerseyEnum.StoreType;

public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String forwardUrl = "/WEB-INF/pages/store";
	private final String forwardListUrl = forwardUrl + "/list.jsp";
	private final String forwardAddUrl = forwardUrl + "/add.jsp";
	private final String forwardUpdateUrl = forwardUrl + "/update.jsp";
	private final String sendRedirectUrl = "/jersey/StoreServlet";
	private StoreService service = new StoreService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ServletContext要放在這, 不能放在實體變數, 因為還沒呼叫init()
		ServletContext sc = getServletConfig().getServletContext();
		String action = request.getParameter("action");
		HttpSession session = request.getSession();

		if (StringUtils.isEmpty(action)) {
			request.setAttribute("storeList", service.getAll());
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("getOne".equals(action)) {
			String forwardUrl;
			try {
				Integer storeId = Integer.valueOf(request.getParameter("storeId"));
				session.setAttribute("store", service.getOne(storeId));
				forwardUrl = forwardUpdateUrl;
			} catch (NumberFormatException e) {
				forwardUrl = forwardAddUrl;
			}
			request.getRequestDispatcher(forwardUrl).forward(request, response);
			return;
		} else if ("create".equals(action)) {
			StoreVO storeVO = new StoreVO();
			storeVO.setName(request.getParameter("name"));
			storeVO.setType(StoreType.valueOf(request.getParameter("type")));
			service.create(storeVO);
			
			//一次只能一個人動商店和托運公司清單
			synchronized (this) {
				//增加servletContext的store清單
				List<StoreVO> list = (List<StoreVO>) sc.getAttribute(storeVO.getType().toString());
				list.add(storeVO);
				sc.setAttribute(storeVO.getType().toString(), list);
			}
			
			List<StoreVO> storeList = new ArrayList<>();
			storeList.add(storeVO);
			request.setAttribute("storeList", storeList);
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("update".equals(action)) {
			Integer storeId = Integer.valueOf(request.getParameter("storeId"));
			StoreVO storeVO = (StoreVO)session.getAttribute("store");
			session.removeAttribute("store");
			
			if (storeVO==null || !storeVO.getStoreId().equals(storeId)) {
				//壞人, 踢回去
				response.sendRedirect(sendRedirectUrl);
				return;
			}

			storeVO.setName(request.getParameter("name"));
			service.update(storeVO);
			
			//一次只能一個人動商店和托運公司清單
			synchronized (this) {
				//增加servletContext的store清單
				List<StoreVO> list = (List<StoreVO>) sc.getAttribute(storeVO.getType().toString());
				list.add(storeVO);
				sc.setAttribute(storeVO.getType().toString(), list);
			}

			List<StoreVO> storeList = new ArrayList<>();
			storeList.add(storeVO);
			request.setAttribute("storeList", storeList);
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("delete".equals(action)) {
			List<StoreVO> storeList = (List<StoreVO>)sc.getAttribute(StoreType.store.toString());
			List<StoreVO> shippingCompanyList = (List<StoreVO>)sc.getAttribute(StoreType.shippingCompany.toString());
			String[] storeIds = request.getParameterValues("storeIds");
			if (storeIds != null) {
				Integer[] ids = new Integer[storeIds.length];
				for (int i = 0; i < storeIds.length; i++) {
					ids[i] = Integer.valueOf(storeIds[i]);
					StoreVO storeVO = new StoreVO();
					storeVO.setStoreId(ids[i]);
					storeList.remove(storeVO);
					shippingCompanyList.remove(storeVO);
				}
				service.delete(ids);
			}
			sc.setAttribute(StoreType.store.toString(), storeList);
			sc.setAttribute(StoreType.shippingCompany.toString(), shippingCompanyList);

			response.sendRedirect(sendRedirectUrl);
			return;
		}

	}
	
	
	
}
