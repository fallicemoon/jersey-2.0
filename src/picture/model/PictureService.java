package picture.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Hibernate;

import commodity.model.CommodityVO;
import tools.HibernateSessionFactory;

public class PictureService {
	private final PictureDAO pictureDAO = new PictureDAO();

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

	public Set<Integer> getPictureIds(Integer commodityId) {
		List<PictureVO> list = pictureDAO.getPictureIds(commodityId);

		Set<Integer> set = new TreeSet<>();
		for (PictureVO pictureVO : list) {
			set.add(pictureVO.getPictureId());
		}
		return set;
	}

	public void getPicrture(Integer pictureId, OutputStream os) {
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

	public Map<Integer, String> getPictureBase64(Integer commodityId) throws IOException {
		List<PictureVO> list = pictureDAO.getPicturesByCommodityId(commodityId);
		Map<Integer, String> pictures = new LinkedHashMap<Integer, String>();

		try {
			for (PictureVO vo : list) {
				String pictureBase64 = parseInputStreamToBase64(vo.getPicture().getBinaryStream());
				pictures.put(vo.getPictureId(), pictureBase64);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pictures;
	}

	public void getPicturesZip(String[] pictureIds, OutputStream os) throws IOException {
		ZipOutputStream zos = new ZipOutputStream(os);

		try {
			//處理fileName可能會有重複的問題
			Map<String, PictureVO> fileNameAndPictureMap = new HashMap<>();
			for (String pictureId : pictureIds) {
				PictureVO vo = pictureDAO.getOne(Integer.valueOf(pictureId));
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

	public void getPicturesZip(Integer commodityId, OutputStream os) throws IOException {
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

	public void deletePictures(String[] pictureIds) {
		Integer[] ids = new Integer[pictureIds.length];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = Integer.valueOf(pictureIds[i]);
		}
		pictureDAO.delete(ids);
	}

	private List<PictureVO> parseFormToPictureVO(List<FileItem> fileItems) throws FileUploadException, IOException {
		List<PictureVO> list = new ArrayList<PictureVO>();

		FileItem picture = null;
		Integer commodityId = null;
		for (FileItem fileItem : fileItems) {
			if (fileItem.isFormField() && fileItem.getFieldName().equals("commodityId")) {
				try {
					commodityId = Integer.valueOf(fileItem.getString("UTF-8"));
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
			// TODO need test
			Blob blob = Hibernate.getLobCreator(HibernateSessionFactory.getSession()).createBlob(picture.get());
			pictureVO.setPicture(blob);
			pictureVO.setFileName(fileName);

			list.add(pictureVO);
		}
		return list;
	}

	private String parseInputStreamToBase64(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int length = -1;
		while ((length = is.read(b)) != -1) {
			baos.write(b, 0, length);
		}

		byte[] pictureByteArray = baos.toByteArray();
		Base64 base64 = new Base64();
		String picture = base64.encodeToString(pictureByteArray);
		return picture;
	}
}
