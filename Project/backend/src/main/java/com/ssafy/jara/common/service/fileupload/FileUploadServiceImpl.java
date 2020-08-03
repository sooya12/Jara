package com.ssafy.jara.common.service.fileupload;

import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadServiceImpl implements FileUploadService{

	//C:\Users\multicampus\Desktop\SSAFY\Project\Sub PJT III\s03p13a308\Project\backend
	String fileUploadRealPath = 
			"C:" + File.separator + "Users" + File.separator + "multicampus" + File.separator + "Desktop" + File.separator + 
			"SSAFY" + File.separator + "Project" + File.separator + "Sub PJT III" + File.separator + 
			"s03p13a308" + File.separator + "Project" + File.separator + "backend" + File.separator + 
			"src" + File.separator + "main" + File.separator + "resources";
	String uploadFolder = "upload";
	
	@Override
	public int fileUpload(String message, MultipartFile file) throws Exception{
		int ret = -1;
		
		File uploadDir = new File(fileUploadRealPath + File.separator + uploadFolder);
		if (!uploadDir.exists()) uploadDir.mkdir();
		
		if( file != null ) {

			// Not Random File Name
			// Use file.getOriginalFilename()
			
			// Random Fild Id & Name
			UUID uuid = UUID.randomUUID();
			
			//file extention
			String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // vs FilenameUtils.getBaseName()
			String savingFileName = uuid + "." + extension;
			
			File saveFile = new File(uploadDir, savingFileName); 
			file.transferTo(saveFile);

			// For DB Table Stuff..
			// 
			
			System.out.println("Message : " + message );
			ret = 1;
		}
		
		return ret;
	}
}
