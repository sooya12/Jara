package com.ssafy.jara.common.service.fileupload;

import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.jara.common.dao.fileupload.FileUploadDao;
import com.ssafy.jara.common.dto.fileupload.ArticleFileUpload;
import com.ssafy.jara.common.dto.fileupload.BarterFileUpload;
import com.ssafy.jara.common.dto.fileupload.TipFileUpload;

@Service
public class FileUploadServiceImpl implements FileUploadService{
	@Autowired
	FileUploadDao fileUploadDao;

	// C:\Users\multicampus\Desktop\SSAFY\Project\Sub PJT III\s03p13a308\Project\backend\src\main\resources
	String fileUploadRealPath = 
			"C:" + File.separator + "Users" + File.separator + "multicampus" + File.separator + "Desktop" + File.separator + 
			"SSAFY" + File.separator + "Project" + File.separator + "Sub PJT III" + File.separator + 
			"s03p13a308" + File.separator + "Project" + File.separator + "backend" + File.separator + 
			"src" + File.separator + "main" + File.separator + "resources";
	
	// AWS
	// /home/ubuntu
//	String fileUploadRealPath = 
//			File.separator + "home" + File.separator + "ubuntu";
	
	String uploadFolder = "imageUpload";
	
	@Override
	public int fileUpload(int id, MultipartFile file, String boardType) throws Exception{
		int ret = -1;
		
		File uploadDir = new File(fileUploadRealPath + File.separator + uploadFolder + File.separator + boardType);
		if (!uploadDir.exists()) uploadDir.mkdir();
		
		if( file != null ) {

			// Not Random File Name
			String originalFileName = file.getOriginalFilename();
			
			// Random Fild Id & Name
			UUID uuid = UUID.randomUUID();
			
			//file extention
			String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // vs FilenameUtils.getBaseName()
			String savingFileName = uuid + "." + extension;
			
			File saveFile = new File(uploadDir, savingFileName); 
			file.transferTo(saveFile);

			System.out.println("ID : " + id );
			
			// For DB Table Stuff..
			switch (boardType) {
			case "article":
				ArticleFileUpload articleFileUpload = new ArticleFileUpload();
				articleFileUpload.setArticle_id(id);
				articleFileUpload.setOrigianl_file_name(originalFileName);
				articleFileUpload.setStored_file_name("" + uuid);
				fileUploadDao.insertArticleFile(articleFileUpload);
				break;
			case "tip":
				TipFileUpload tipFileUpload = new TipFileUpload();
				tipFileUpload.setTip_id(id);
				tipFileUpload.setOrigianl_file_name(originalFileName);
				tipFileUpload.setStored_file_name("" + uuid);
				fileUploadDao.insertTipFile(tipFileUpload);
				break;
			case "barter":
				BarterFileUpload barterFileUpload = new BarterFileUpload();
				barterFileUpload.setItem_id(id);
				barterFileUpload.setOrigianl_file_name(originalFileName);
				barterFileUpload.setStored_file_name("" + uuid);
				fileUploadDao.insertBarterFile(barterFileUpload);
				break;
			}
			
			ret = 1;
		}
		
		return ret;
	}
}
