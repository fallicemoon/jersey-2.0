package purchaseCase.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import purchaseCase.model.PurchaseCaseService;
import purchaseCase.model.PurchaseCaseVO;
import store.model.StoreService;

public class PurchaseCaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String sendRedirectUrl = "/jersey/PurchaseCaseServlet";
	private final String forwardUrl = "/WEB-INF/pages/purchaseCase";
	private final String forwardListUrl = forwardUrl + "/list.jsp";
	private final String forwardAddUrl = forwardUrl + "/add.jsp";
	private final String forwardUpdateUrl = forwardUrl + "/update.jsp";
	private final String forwardAddCommodityUrl = forwardUrl + "/addCommodity.jsp?purchaseCaseId=%s";
	private final PurchaseCaseService service = new PurchaseCaseService();
	private final StoreService storeService = new StoreService();

	public String getForwardAddCommodityUrl(Integer purchaseCaseId) {
		return String.format(forwardAddCommodityUrl, purchaseCaseId);
	}

	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		
		if (StringUtils.isEmpty(action)) {
			request.setAttribute("purchaseCaseList", service.getAll());
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
		} 
//		else if ("getProgressNotComplete".equals(action)) {
//			request.setAttribute("purchaseCaseList", service.getAllOfNotComplete());
//			request.getRequestDispatcher(forwardListUrl).forward(request, response);
//		} 
		else if ("getOne".equals(action)) {
			//取出可以選的商店和託運公司
			try {
				// update
				Integer purchaseCaseId = Integer.valueOf(request.getParameter("purchaseCaseId"));
				//放在session, 這樣才不用再update的時候再重新get一次
				session.setAttribute("purchaseCase", service.getOne(purchaseCaseId));
				request.getRequestDispatcher(forwardUpdateUrl).forward(request, response);
			} catch (NumberFormatException e) {
				// create
				request.getRequestDispatcher(forwardAddUrl).forward(request, response);
			}
		} else if ("getCommodityList".equals(action)) {
			try {
				// 取得已經在進貨單中的商品清單
				Integer purchaseCaseId = Integer.valueOf(request.getParameter("purchaseCaseId"));
				request.setAttribute("commodityListInPurchaseCase", service.getCommoditysByPurchaseCaseId(purchaseCaseId));

				// 取得可以新增在進貨單中的商品清單
				request.setAttribute("commodityListNotInPurchaseCase", service.getCommoditysByPurchaseCaseIdIsNull());
				request.getRequestDispatcher(getForwardAddCommodityUrl(purchaseCaseId)).forward(request, response);
			} catch (NumberFormatException e) {
				// 沒有purchaseCaseId, 導回進貨清單
				response.sendRedirect(sendRedirectUrl);
			}

		} else if ("create".equals(action)) {
			PurchaseCaseVO purchaseCaseVO = new PurchaseCaseVO();
			LinkedHashSet<String> errors = new LinkedHashSet<String>();

			String store = request.getParameter("store").trim();
			String progress = request.getParameter("progress").trim();
			String shippingCompany = request.getParameter("shippingCompany").trim();
			String trackingNumber = request.getParameter("trackingNumber").trim();
			String trackingNumberLink = request.getParameter("trackingNumberLink").trim();
			String agent = request.getParameter("agent").trim();
			String agentTrackingNumber = request.getParameter("agentTrackingNumber").trim();
			String agentTrackingNumberLink = request.getParameter("agentTrackingNumberLink").trim();
			String description = request.getParameter("description").trim();
			Boolean isAbroad = Boolean.valueOf(request.getParameter("isAbroad"));
			try {
				purchaseCaseVO.setCost(Integer.valueOf(request.getParameter("cost")));
			} catch (NumberFormatException e) {
				errors.add("成本需為數字!");
			}
			try {
				purchaseCaseVO.setAgentCost(Integer.valueOf(request.getParameter("agentCost")));
			} catch (NumberFormatException e) {
				errors.add("國際運費需為數字!");
			}
			try {
				purchaseCaseVO.setStore(storeService.getOne(Integer.valueOf(store)));
				purchaseCaseVO.setShippingCompany(storeService.getOne(Integer.valueOf(shippingCompany)));
			} catch (NumberFormatException e) {
				errors.add("請不要對商店和托運公司做壞壞的事");
			}
			purchaseCaseVO.setProgress(progress);
			purchaseCaseVO.setTrackingNumber(trackingNumber);
			purchaseCaseVO.setTrackingNumberLink(trackingNumberLink);
			purchaseCaseVO.setAgent(agent);
			purchaseCaseVO.setAgentTrackingNumber(agentTrackingNumber);
			purchaseCaseVO.setAgentTrackingNumberLink(agentTrackingNumberLink);
			purchaseCaseVO.setDescription(description);
			purchaseCaseVO.setIsAbroad(isAbroad);

			if (!errors.isEmpty()) {
				request.setAttribute("errors", errors);
				request.setAttribute("purchaseCase", purchaseCaseVO);
				request.getRequestDispatcher(forwardAddUrl).forward(request, response);
				return;
			}

			purchaseCaseVO.setTime(new Date());

			service.create(purchaseCaseVO);
			// HttpSession session = request.getSession();
			// Integer[] commodityIds = (Integer[])
			// session.getAttribute("commodityIds");
			// if (commodityIds != null)
			// service.addPurchaseCaseIdToCommoditys(purchaseCaseId,
			// commodityIds);
			// session.removeAttribute("commodityIds");

			List<PurchaseCaseVO> purchaseCaseList = new ArrayList<>();
			purchaseCaseList.add(purchaseCaseVO);
			request.setAttribute("purchaseCaseList", purchaseCaseList);
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
			return;
		} else if ("update".equals(action)) {
			Integer purchaseCaseId = Integer.valueOf(request.getParameter("purchaseCaseId"));
			PurchaseCaseVO purchaseCaseVO = (PurchaseCaseVO)session.getAttribute("purchaseCase");
			session.removeAttribute("purchaseCase");
			LinkedHashSet<String> errors = new LinkedHashSet<String>();
			
			if (purchaseCaseVO==null || !purchaseCaseVO.getPurchaseCaseId().equals(purchaseCaseId)) {
				//有壞人進來惹, 給我滾回進貨頁
				response.sendRedirect(sendRedirectUrl);
				return;
			}

			Integer store;
			Integer shippingCompany;
			try {
				store = Integer.valueOf(request.getParameter("store"));
				shippingCompany = Integer.valueOf(request.getParameter("shippingCompany"));
				purchaseCaseVO.setStore(storeService.getOne(store));
				purchaseCaseVO.setShippingCompany(storeService.getOne(shippingCompany));
			} catch (NumberFormatException e) {
				errors.add("請不要對商店和託運公司的ID做壞壞的事");
			}
			String progress = request.getParameter("progress").trim();
			String trackingNumber = request.getParameter("trackingNumber").trim();
			String trackingNumberLink = request.getParameter("trackingNumberLink").trim();
			String agent = request.getParameter("agent").trim();
			String agentTrackingNumber = request.getParameter("agentTrackingNumber").trim();
			String agentTrackingNumberLink = request.getParameter("agentTrackingNumberLink").trim();
			String description = request.getParameter("description").trim();
			Boolean isAbroad = Boolean.valueOf(request.getParameter("isAbroad"));
			Integer cost = 0;
			try {
				cost = Integer.valueOf(request.getParameter("cost"));
			} catch (NumberFormatException e) {
				errors.add("成本需為數字");
			}
			Integer agentCost = 0;
			try {
				agentCost = Integer.valueOf(request.getParameter("agentCost"));
			} catch (NumberFormatException e) {
				errors.add("國際運費需為數字!");
			}

			purchaseCaseVO.setProgress(progress);
			purchaseCaseVO.setTrackingNumber(trackingNumber);
			purchaseCaseVO.setTrackingNumberLink(trackingNumberLink);
			purchaseCaseVO.setAgent(agent);
			purchaseCaseVO.setAgentTrackingNumber(agentTrackingNumber);
			purchaseCaseVO.setAgentTrackingNumberLink(agentTrackingNumberLink);
			purchaseCaseVO.setDescription(description);
			purchaseCaseVO.setIsAbroad(isAbroad);
			purchaseCaseVO.setCost(cost);
			purchaseCaseVO.setAgentCost(agentCost);

			if (!errors.isEmpty()) {
				request.setAttribute("errors", errors);
				request.getRequestDispatcher(forwardUpdateUrl).forward(request, response);
				return;
			}

			service.update(purchaseCaseVO);

			//因為匯入商品可能在別的瀏覽器分頁更新了, 所以這邊再取一次避免顯示的商品和DB不一致
			List<PurchaseCaseVO> purchaseCaseList = new ArrayList<>();
			purchaseCaseList.add(service.getOne(purchaseCaseId));
			request.setAttribute("purchaseCaseList", purchaseCaseList);
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
		} else if ("delete".equals(action)) {
			String[] purchaseCaseIds = request.getParameterValues("purchaseCaseIds");
			if (purchaseCaseIds != null) {
				Integer[] ids = new Integer[purchaseCaseIds.length];
				for (int i = 0; i < purchaseCaseIds.length; i++) {
					ids[i] = Integer.valueOf(purchaseCaseIds[i]);
				}
				service.delete(ids);
			}
			response.sendRedirect(sendRedirectUrl);
		} else if ("addPurchaseCaseId".equals(action)) {
			Integer purchaseCaseId = Integer.valueOf(request.getParameter("purchaseCaseId"));
			String[] commodityIds = request.getParameterValues("commodityIds");
			if (commodityIds != null) {
				Integer[] ids = new Integer[commodityIds.length];
				for (int i = 0; i < commodityIds.length; i++) {
					ids[i] = Integer.valueOf(commodityIds[i]);
				}
				service.addPurchaseCaseIdToCommoditys(purchaseCaseId, ids);
			}
			// 避免不同帳號互相干擾 每次都要重讀DB
			try {
				// 取得已經在進貨單中的商品清單
				request.setAttribute("commodityListInPurchaseCase", service.getCommoditysByPurchaseCaseId(purchaseCaseId));

				// 取得可以新增在進貨單中的商品清單
				request.setAttribute("commodityListNotInPurchaseCase", service.getCommoditysByPurchaseCaseIdIsNull());
			} catch (NumberFormatException e) {
				// 沒有purchaseCaseId, 導回進貨清單
				response.sendRedirect(sendRedirectUrl);
				return;
			}

			request.getRequestDispatcher(getForwardAddCommodityUrl(purchaseCaseId)).forward(request, response);
		} else if ("deletePurchaseCaseId".equals(action)) {
			Integer purchaseCaseId = Integer.valueOf(request.getParameter("purchaseCaseId"));
			String[] commodityIds = request.getParameterValues("commodityIds");
			if (commodityIds != null) {
				// 有勾商品
				Integer[] ids = new Integer[commodityIds.length];
				for (int i = 0; i < commodityIds.length; i++) {
					ids[i] = Integer.valueOf(commodityIds[i]);
				}
				service.deletePurchasCaseIdFromCommoditys(ids);
			}
			// 避免不同帳號互相干擾 每次都要重讀DB
			try {
				// 取得已經在進貨單中的商品清單
				request.setAttribute("commodityListInPurchaseCase", service.getCommoditysByPurchaseCaseId(purchaseCaseId));

				// 取得可以新增在進貨單中的商品清單
				request.setAttribute("commodityListNotInPurchaseCase", service.getCommoditysByPurchaseCaseIdIsNull());
			} catch (NumberFormatException e) {
				// 沒有purchaseCaseId, 導回進貨清單
				response.sendRedirect(sendRedirectUrl);
				return;
			}
			request.getRequestDispatcher(getForwardAddCommodityUrl(purchaseCaseId)).forward(request, response);
		}
		
	}
	
	
	
}
