package com.ssafy.jara.common.controller.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.jara.common.service.fileupload.FileUploadService;

@Controller
public class FileUploadController {
	
	@Autowired
	FileUploadService fileUploadService;
	
	@GetMapping("/fileupload")
	public String goFileUploadPage() {
		return "fileUpload";
	}
	
	@PostMapping(value = "/fileupload")
	@ResponseBody
	public int fileUpload(String message, MultipartFile file) throws Exception{
		return fileUploadService.fileUpload(message, file);
	}
}
