package picture.controller;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;

import commodity.model.CommodityService;
import picture.model.PictureService;

public class PictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String sendRedirectCommodityUrl = "/jersey/CommodityServlet";
	private final String sendRedirectUrl = "/jersey/PictureServlet?commodityId=";
	private final String forwardUrl = "/WEB-INF/pages/picture";
	private final String forwardUploadPictureUrl = forwardUrl + "/uploadPicture.jsp";
	private final String pictureContentType = "image/*";
	private final PictureService service = new PictureService();
	private final CommodityService commodityService = new CommodityService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		// 取得圖片網址, 不需要forward
		if ("getPicture".equals(action)) {
			Integer pictureId = Integer.valueOf(request.getParameter("pictureId"));
			response.setContentType(pictureContentType);
			service.getPicrture(pictureId, response.getOutputStream());
			return;
		}

		//除了取得圖片網址, 進此servlet必帶commodityId, 否則導回商品頁
		Integer commodityId;
		try {
			commodityId = Integer.valueOf(request.getParameter("commodityId"));
		} catch (NumberFormatException e) {
			response.sendRedirect(sendRedirectCommodityUrl);
			return;
		}
		Set<String> errors = new LinkedHashSet<String>();

		if (StringUtils.isEmpty(action)) {
			// 上傳圖片
			if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
				try {
					service.uploadPicture(request);
					response.sendRedirect(sendRedirectUrl+commodityId);
					return;
				} catch (SizeLimitExceededException e) {
					e.printStackTrace();
					errors.add("上傳檔案需小於30MB!");
					request.setAttribute("errors", errors);
				} catch (FileUploadException e) {
					e.printStackTrace();
					errors.add(e.getMessage());
					request.setAttribute("errors", errors);
				}
			}

			// 取得所有圖片
			Set<Integer> pictureIds = service.getPictureIds(commodityId);
			request.setAttribute("pictureIds", pictureIds);
			request.setAttribute("commodity", commodityService.getOne(commodityId));
			request.getRequestDispatcher(forwardUploadPictureUrl).forward(request, response);
			return;
		} else if ("delete".equals(action)) {
			String[] pictureIds = request.getParameterValues("pictureId");
			if (pictureIds != null) {
				service.deletePictures(pictureIds);
			}
			response.sendRedirect(sendRedirectUrl+commodityId);
			return;
		} else if ("download".equals(action)) {
			String[] pictureIds = request.getParameterValues("pictureId");
			if (pictureIds != null) {
				response.setHeader("Content-disposition", "attachment; filename=" + commodityId + ".zip");
				service.getPicturesZip(pictureIds, response.getOutputStream());
			} else {
				 response.sendRedirect(sendRedirectUrl+commodityId);
			}

		} else if ("downloadAll".equals(action)) {
			response.setHeader("Content-disposition", "attachment; filename=" + commodityId + "All.zip");
			service.getPicturesZip(commodityId, response.getOutputStream());
		}
		// response.sendRedirect(sendRedirectUrl+commodityId);
		// return;
	}
}
