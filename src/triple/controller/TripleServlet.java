package triple.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import commodity.model.CommodityService;
import commodity.model.CommodityVO;
import commodity.model.CommodityWithPicCountVO;
import purchaseCase.model.PurchaseCaseService;
import purchaseCase.model.PurchaseCaseVO;
import sellCase.model.SellCaseService;
import sellCase.model.SellCaseVO;
import sellCase.model.SellCaseWithBenefitVO;
import triple.model.TripleService;

public class TripleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CommodityService cs = new CommodityService();
	private PurchaseCaseService pcs = new PurchaseCaseService();
	private SellCaseService scs = new SellCaseService();
	private TripleService ts = new TripleService();

	private final String forwardUrl = "/WEB-INF/pages";
	private final String forwardListOneUrl = forwardUrl + "/listOne.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("commodity".equals(action)) {
			List<CommodityWithPicCountVO> commodityList = new ArrayList<>();
			List<PurchaseCaseVO> purchaseCaseList = new ArrayList<>();
			List<SellCaseWithBenefitVO> sellCaseWithBenefitList = new ArrayList<>();

			CommodityVO commodityVO = cs.getOne(Integer.valueOf(request.getParameter("commodityId")));
			ts.generateTriple(commodityVO, commodityList, purchaseCaseList, sellCaseWithBenefitList);

			request.setAttribute("title", "商品:" + commodityVO.getCommodityId() + "/" + commodityVO.getItemName());
			request.setAttribute("commodityList", commodityList);
			request.setAttribute("purchaseCaseList", purchaseCaseList);
			request.setAttribute("sellCaseList", sellCaseWithBenefitList);

			request.getRequestDispatcher(forwardListOneUrl).forward(request, response);
			return;
		} else if ("purchaseCase".equals(action)) {
			Set<CommodityWithPicCountVO> commoditys = new LinkedHashSet<>();
			List<PurchaseCaseVO> purchaseCaseList = new ArrayList<>();
			List<SellCaseWithBenefitVO> sellCaseList = new ArrayList<>();

			PurchaseCaseVO purchaseCaseVO = pcs.getOne(Integer.valueOf(request.getParameter("purchaseCaseId")));
			ts.generateTriple(purchaseCaseVO, commoditys, purchaseCaseList, sellCaseList);

			request.setAttribute("title",
					"進貨:" + purchaseCaseVO.getPurchaseCaseId() + "/" + purchaseCaseVO.getStore().getName());
			request.setAttribute("commodityList", commoditys);
			request.setAttribute("purchaseCaseList", purchaseCaseList);
			request.setAttribute("sellCaseList", sellCaseList);

			request.getRequestDispatcher(forwardListOneUrl).forward(request, response);
			return;
		} else if ("sellCase".equals(action)) {
			Set<CommodityWithPicCountVO> commoditys = new LinkedHashSet<>();
			Set<PurchaseCaseVO> purchaseCases = new LinkedHashSet<>();
			List<SellCaseWithBenefitVO> sellCaseList = new ArrayList<>();
			SellCaseVO sellCaseVO = scs.getOne(Integer.valueOf(request.getParameter("sellCaseId")));

			ts.generateTriple(sellCaseVO, commoditys, purchaseCases, sellCaseList);

			request.setAttribute("title", "出貨:" + sellCaseVO.getSellCaseId() + "/" + sellCaseVO.getAddressee());
			request.setAttribute("commodityList", commoditys);
			request.setAttribute("purchaseCaseList", purchaseCases);
			request.setAttribute("sellCaseList", sellCaseList);

			request.getRequestDispatcher(forwardListOneUrl).forward(request, response);
		}
	}

}
