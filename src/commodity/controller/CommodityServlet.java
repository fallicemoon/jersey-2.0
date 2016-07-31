package commodity.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import commodity.model.CommodityService;
import commodity.model.CommodityVO;
import commodity.model.CommodityWithPicCountVO;

public class CommodityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String sendResponseUrl = "/jersey/CommodityServlet";
	private final String forwardUrl = "/WEB-INF/pages/commodity";
	private final String forwardListUrl = forwardUrl + "/list.jsp";
	private final String forwardAddUrl = forwardUrl + "/add.jsp";
	private final String forwardUpdateUrl = forwardUrl + "/update.jsp";
	private CommodityService service = new CommodityService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();

		if (StringUtils.isEmpty(action)) {
			List<CommodityWithPicCountVO> commodityList = service.getAll();
			Map<String, Set<String>> ruleMap = service.getRule(commodityList);
			for (String key : ruleMap.keySet()) {
				request.setAttribute(key, ruleMap.get(key));
			}
			
			request.setAttribute("commodityList", commodityList);
			request.setAttribute("showRule", true);
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
		} else if ("getOne".equals(action)) {
			//用在create和update的時候去DB取出資料
			try {
				Integer commodityId = Integer.valueOf(request.getParameter("commodityId"));
				CommodityVO commodityVO = service.getOne(commodityId);
				session.setAttribute("commodity", service.getOne(commodityId));
			} catch (NumberFormatException e) {
				//create
				request.getRequestDispatcher(forwardAddUrl).forward(request, response);
				return;
			}
			//update
			request.getRequestDispatcher(forwardUrl + "/update.jsp").forward(request, response);
		}
		//改成由前端頁面jQuery篩選
//		else if ("getByRule".equals(action)) {
//			String itemName = request.getParameter("itemName");
//			String player = request.getParameter("player");
//			String team = request.getParameter("team");
//			String style = request.getParameter("style");
//			String brand = request.getParameter("brand");
//			String size = request.getParameter("size");
//			String level = request.getParameter("level");
//			String condition = request.getParameter("condition");
//			String tag = request.getParameter("tag");
//			String owner = request.getParameter("owner");
//			String sellPlatform = request.getParameter("sellPlatform");
//			String isStored = request.getParameter("isStored");
//
//			Map<String, Object> rule = new HashMap<String, Object>();
//			if (!itemName.equals("無"))
//				rule.put("itemName", itemName);
//			if (!player.equals("無"))
//				rule.put("player", player);
//			if (!team.equals("無"))
//				rule.put("team", team);
//			if (!style.equals("無"))
//				rule.put("style", style);
//			if (!brand.equals("無"))
//				rule.put("brand", brand);
//			if (!size.equals("無"))
//				rule.put("size", size);
//			if (!level.equals("無"))
//				rule.put("level", level);
//			if (!condition.equals("無"))
//				rule.put("condition", condition);
//			if (!tag.equals("無"))
//				rule.put("tag", tag);
//			if (!owner.equals("無"))
//				rule.put("owner", owner);
//			if (!sellPlatform.equals("無"))
//				rule.put("sellPlatform", sellPlatform);
//			if (!isStored.equals("無"))
//				rule.put("isStored", Boolean.valueOf(isStored));
//			
//			List<CommodityWithPicCountVO> commodityList = service.getByRule(rule);
//			Map<String, Set<String>> ruleMap = service.getRule(commodityList);
//			for (String key : ruleMap.keySet()) {
//				request.setAttribute(key, ruleMap.get(key));
//			}
//			request.setAttribute("commodityList", commodityList);
//			request.setAttribute("showRule", false);
//			request.getRequestDispatcher(forwardListUrl).forward(request, response);
//		} 
		else if ("create".equals(action)) {
				CommodityVO commodityVO = new CommodityVO();
				LinkedHashSet<String> errors = new LinkedHashSet<String>();

				commodityVO.setItemName(request.getParameter("itemName").trim());
				commodityVO.setQty(Integer.valueOf(1));
				commodityVO.setStyle(request.getParameter("style").trim());
				commodityVO.setLink(request.getParameter("link").trim());
				commodityVO.setPlayer(request.getParameter("player").trim());
				commodityVO.setNumber(request.getParameter("number").trim());
				commodityVO.setSeason(request.getParameter("season").trim());
				commodityVO.setTeam(request.getParameter("team").trim());
				commodityVO.setColor(request.getParameter("color").trim());
				commodityVO.setBrand(request.getParameter("brand").trim());
				commodityVO.setSize(request.getParameter("size").trim());
				commodityVO.setLevel(request.getParameter("level").trim());
				commodityVO.setCondition(request.getParameter("condition").trim());
				commodityVO.setTag(request.getParameter("tag").trim());
				commodityVO.setPatchAndCertificate(request.getParameter("patchAndCertificate").trim());
				commodityVO.setSerial(request.getParameter("serial").trim());
				commodityVO.setOwner(request.getParameter("owner").trim());
				commodityVO.setSellPlatform(request.getParameter("sellPlatform").trim());
				commodityVO.setIsStored(Boolean.valueOf(true));
				try {
					commodityVO.setCost(Integer.valueOf(request.getParameter("cost").trim()));
				} catch (NumberFormatException e) {
					errors.add("成本需輸入數字!");
				}
				try {
					commodityVO.setSellPrice(Integer.valueOf(request.getParameter("sellPrice").trim()));
				} catch (NumberFormatException e) {
					errors.add("售價需輸入數字!");
				}
				if (!errors.isEmpty()) {
					request.setAttribute("errors", errors);
					request.setAttribute("commodity", commodityVO);
					request.getRequestDispatcher(forwardAddUrl).forward(request, response);
					return;
				}

			
			service.create(commodityVO);
			
			List<CommodityWithPicCountVO> list = new ArrayList<>();
			list.add(service.getCommodityWithPicCountVO(commodityVO));
			request.setAttribute("commodityList", list);
			
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
		} else if ("update".equals(action)) {
			Integer commodityId = Integer.valueOf(request.getParameter("commodityId").trim());
			CommodityVO commodityVO = (CommodityVO)session.getAttribute("commodity");
			session.removeAttribute("commodity");
			
			if (commodityVO==null || !commodityVO.getCommodityId().equals(commodityId)) {
				//有壞人進來惹, 給我滾回商品頁
				response.sendRedirect(sendResponseUrl);
				return;
			}
			
			LinkedHashSet<String> errors = new LinkedHashSet<String>();
			commodityVO.setItemName(request.getParameter("itemName").trim());
			try {
				commodityVO.setQty(Integer.valueOf(request.getParameter("qty").trim()));
			} catch (NumberFormatException e1) {
				errors.add("Qty需輸入數字!");
			}
			commodityVO.setStyle(request.getParameter("style").trim());
			try {
				commodityVO.setCost(Integer.valueOf(request.getParameter("cost").trim()));
			} catch (NumberFormatException e) {
				errors.add("成本需輸入數字!");
			}
			try {
				commodityVO.setSellPrice(Integer.valueOf(request.getParameter("sellPrice").trim()));
			} catch (NumberFormatException e) {
				errors.add("售價需輸入數字!");
			}
			if (!errors.isEmpty()) {
				request.setAttribute("commodityId", commodityId);
				request.getRequestDispatcher(forwardUpdateUrl).forward(request, response);
				return;
			}
			commodityVO.setLink(request.getParameter("link").trim());
			commodityVO.setPlayer(request.getParameter("player").trim());
			commodityVO.setNumber(request.getParameter("number").trim());
			commodityVO.setSeason(request.getParameter("season").trim());
			commodityVO.setTeam(request.getParameter("team").trim());
			commodityVO.setColor(request.getParameter("color").trim());
			commodityVO.setBrand(request.getParameter("brand").trim());
			commodityVO.setSize(request.getParameter("size").trim());
			commodityVO.setLevel(request.getParameter("level").trim());
			commodityVO.setCondition(request.getParameter("condition").trim());
			commodityVO.setTag(request.getParameter("tag").trim());
			commodityVO.setPatchAndCertificate(request.getParameter("patchAndCertificate").trim());
			commodityVO.setSerial(request.getParameter("serial").trim());
			commodityVO.setOwner(request.getParameter("owner").trim());
			commodityVO.setSellPlatform(request.getParameter("sellPlatform").trim());
			commodityVO.setIsStored(Boolean.valueOf(request.getParameter("isStored")));

			service.update(commodityVO);
			
			List<CommodityWithPicCountVO> list = new ArrayList<>();
			list.add(service.getCommodityWithPicCountVO(commodityVO));
			request.setAttribute("commodityList", list);
			request.getRequestDispatcher(forwardListUrl).forward(request, response);
		} else if ("delete".equals(action)) {
			String[] commodityIds = request.getParameterValues("commodityIds");
			if (commodityIds != null) {
				Integer[] ids = new Integer[commodityIds.length];
				for (int i = 0; i < commodityIds.length; i++) {
					ids[i] = Integer.valueOf(commodityIds[i]);
				}
				service.delete(ids);
			}
			response.sendRedirect(sendResponseUrl);
		} else if ("copy".equals(action)) {
			String[] commodityIds = request.getParameterValues("commodityIds");
			if ((commodityIds != null) && (commodityIds.length == 1)) {
				String commodityId = commodityIds[0];
				CommodityVO commodityVO = service.getOne(Integer.valueOf(commodityId));
				service.create(commodityVO);
			}
			response.sendRedirect(sendResponseUrl);
		}
	}

}
