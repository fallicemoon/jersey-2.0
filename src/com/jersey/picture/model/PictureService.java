package com.jersey.picture.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.commodity.model.CommodityDAO;
import com.jersey.commodity.model.CommodityVO;
import com.jersey.tools.HibernateTools;
import com.jersey.tools.JerseyEnum.Authority;
import com.jersey.userConfig.model.UserSession;

@Service
public class PictureService {

	@Autowired
	private HibernateTools hibernateTools;
	@Autowired
	private CommodityDAO commodityDAO;
	@Autowired
	private PictureDAO pictureDAO;
	@Autowired
	private UserSession userSession;

	// 單檔上限30MB
	private final Long singleFileSizeMax = 31457280L;

	public void uploadPicture(HttpServletRequest request)
			throws SizeLimitExceededException, IOException, FileUploadException {
		if (!ServletFileUpload.isMultipartContent(request))
			return;

		DiskFileItemFactory factory = new DiskFileItemFactory();

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(singleFileSizeMax);
		upload.setHeaderEncoding("UTF-8");

		List<FileItem> items = upload.parseRequest(request);
		List<PictureVO> pictureVOList = parseFormToPictureVO(items);
		
		for (PictureVO pictureVO : pictureVOList) {
			pictureDAO.create(pictureVO);
		}
	}

	public Set<String> getPictureIds(String commodityId) {
		List<PictureVO> list = pictureDAO.getPictureIds(commodityId);

		Set<String> set = new TreeSet<>();
		for (PictureVO pictureVO : list) {
			set.add(pictureVO.getPictureId());
		}
		return set;
	}

	public void getPicrture(String pictureId, OutputStream os) {
		try {
			InputStream is = pictureDAO.getOne(pictureId).getPicture().getBinaryStream();
			byte[] b = new byte[4096];
			int length;
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			os.flush();
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

//	public Map<Integer, String> getPictureBase64(Integer commodityId) throws IOException {
//		List<PictureVO> list = pictureDAO.getPicturesByCommodityId(commodityId);
//		Map<Integer, String> pictures = new LinkedHashMap<Integer, String>();
//
//		try {
//			for (PictureVO vo : list) {
//				String pictureBase64 = parseInputStreamToBase64(vo.getPicture().getBinaryStream());
//				pictures.put(vo.getPictureId(), pictureBase64);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return pictures;
//	}

	public void getPicturesZip(String[] pictureIds, OutputStream os) throws IOException {
		ZipOutputStream zos = new ZipOutputStream(os);

		try {
			//處理fileName可能會有重複的問題
			Map<String, PictureVO> fileNameAndPictureMap = new HashMap<>();
			for (String pictureId : pictureIds) {
				PictureVO vo = pictureDAO.getOne(pictureId);
				PictureVO previous = fileNameAndPictureMap.put(vo.getFileName(), vo);
				if (previous!=null) {
					int i = 2;
				    do {
				    	String fileName = previous.getFileName();
				    	String file = fileName.substring(0, fileName.indexOf("."));
				    	int j = i-1;
				    	if (file.substring(file.length()-3).equals("("+j+")")) {
				    		file = file.substring(0, file.length()-3);
				    	}
				    	String extension = fileName.substring(fileName.indexOf("."));
				    	previous.setFileName(file + "(" + i + ")" + extension);
						i++;
					} while (fileNameAndPictureMap.get(previous.getFileName())!=null);
				    fileNameAndPictureMap.put(previous.getFileName(), previous);
				}
			}
			
			//開始生成zip檔
			for (String fileName : fileNameAndPictureMap.keySet()) {
				PictureVO vo = fileNameAndPictureMap.get(fileName);
				InputStream is = vo.getPicture().getBinaryStream();
				zos.putNextEntry(new ZipEntry(vo.getFileName()));
				byte[] b = new byte[1024];
				int length;
				while ((length = is.read(b)) != -1) {
					zos.write(b, 0, length);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		zos.close();
		os.close();
	}

	public void getPicturesZip(String commodityId, OutputStream os) throws IOException {
		List<PictureVO> list = pictureDAO.getPicturesByCommodityId(commodityId);
		ZipOutputStream zos = new ZipOutputStream(os);

		try {
			//處理fileName可能會有重複的問題
			Map<String, PictureVO> fileNameAndPictureMap = new HashMap<>();
			for (PictureVO picture : list) {
				PictureVO previous = fileNameAndPictureMap.put(picture.getFileName(), picture);
				if (previous!=null) {
					int i = 2;
				    do {
				    	String fileName = previous.getFileName();
				    	String file = fileName.substring(0, fileName.indexOf("."));
				    	int j = i-1;
				    	if (file.substring(file.length()-3).equals("("+j+")")) {
				    		file = file.substring(0, file.length()-3);
				    	}
				    	String extension = fileName.substring(fileName.indexOf("."));
				    	previous.setFileName(file + "(" + i + ")" + extension);
						i++;
					} while (fileNameAndPictureMap.get(previous.getFileName())!=null);
				    fileNameAndPictureMap.put(previous.getFileName(), previous);
				}
			}
			
			//開始生成zip檔
			for (String fileName : fileNameAndPictureMap.keySet()) {
				PictureVO vo = fileNameAndPictureMap.get(fileName);
				InputStream is = vo.getPicture().getBinaryStream();
				zos.putNextEntry(new ZipEntry(vo.getFileName()));
				byte[] b = new byte[1024];
				int length;
				while ((length = is.read(b)) != -1) {
					zos.write(b, 0, length);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		zos.close();
		os.close();
	}

	public boolean deletePictures(Integer[] pictureIds) {
		return pictureDAO.delete(pictureIds);
	}
	
	//權限控管
	public boolean validatePicPageAuthority (String commodityId) {
		Authority userAuthority = userSession.getUserConfigVO().getAuthority();
		Authority commodityAuthority = commodityDAO.getOne(commodityId).getAuthority();
		return commodityAuthority==Authority.CUSTOMER || userAuthority==Authority.ADMIN;
	}
	
	//權限控管
	public boolean validateReadPicAuthority (String pictureId) {
		Authority userAuthority = userSession.getUserConfigVO().getAuthority();
		Authority commodityAuthority = pictureDAO.getOne(pictureId).getCommodityVO().getAuthority();
		return commodityAuthority==Authority.CUSTOMER || userAuthority==Authority.ADMIN;
	}

	private List<PictureVO> parseFormToPictureVO(List<FileItem> fileItems) throws FileUploadException, IOException {
		List<PictureVO> list = new ArrayList<PictureVO>();
		FileItem picture = null;
		String commodityId = null;
		for (FileItem fileItem : fileItems) {
			if (fileItem.isFormField() && fileItem.getFieldName().equals("commodityId")) {
				try {
					commodityId = fileItem.getString("UTF-8");
				} catch (NumberFormatException e) {
					throw new FileUploadException("商品ID格式錯誤");
				}
			} else if (!fileItem.isFormField() && fileItem.getFieldName().equals("picture")) {
				picture = fileItem;
			}
		}

		PictureVO pictureVO = new PictureVO();
		CommodityVO commodityVO = new CommodityVO();
		commodityVO.setCommodityId(commodityId);
		pictureVO.setCommodityVO(commodityVO);

		if (commodityId!=null && picture!=null) {
			String fileName = picture.getName();
			String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
			if ((!extensionName.equalsIgnoreCase("jpg")) && (!extensionName.equalsIgnoreCase("gif")) && (!extensionName.equalsIgnoreCase("png")))
				throw new FileUploadException("副檔名須為jpg, gif, png 三者其中之一");
			Blob blob = hibernateTools.getBlob(picture.get());
			pictureVO.setPicture(blob);
			pictureVO.setFileName(fileName);

			list.add(pictureVO);
		}
		return list;
	}

//	private String parseInputStreamToBase64(InputStream is) throws IOException {
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		byte[] b = new byte[1024];
//		int length = -1;
//		while ((length = is.read(b)) != -1) {
//			baos.write(b, 0, length);
//		}
//
//		byte[] pictureByteArray = baos.toByteArray();
//		Base64 base64 = new Base64();
//		String picture = base64.encodeToString(pictureByteArray);
//		return picture;
//	}
}
