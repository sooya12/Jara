package com.ssafy.jara.common.controller.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.jara.common.service.fileupload.FileUploadService;

import io.swagger.annotations.ApiOperation;

//@Controller
//public class FileUploadController {
//	
//	@Autowired
//	FileUploadService fileUploadService;
//	
//	@GetMapping("/fileupload")
//	public String goFileUploadPage() {
//		return "fileUpload";
//	}
//	
//	@PostMapping(value = "/fileupload")
//	@ResponseBody
//	public int fileUpload(String message, MultipartFile file) throws Exception{
//		return fileUploadService.fileUpload(message, file);
//	}
//}

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/fileupload")
public class FileUploadController {
	
	@Autowired
	FileUploadService fileUploadService;
	
	@ApiOperation(value = "Article 파일 업로드", response = String.class)
	@PostMapping("/article/{id}")
	private ResponseEntity<String> articleFileUpload(@PathVariable("id") int id, MultipartFile file) throws Exception {
		int ret = fileUploadService.fileUpload(id, file, "article");
		if (ret > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Tip 파일 업로드", response = String.class)
	@PostMapping("/tip/{id}")
	private ResponseEntity<String> tipFileUpload(@PathVariable("id") int id, MultipartFile file) throws Exception {
		int ret = fileUploadService.fileUpload(id, file, "tip");
		if (ret > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Barter 파일 업로드", response = String.class)
	@PostMapping("/barter/{id}")
	private ResponseEntity<String> barterFileUpload(@PathVariable("id") int id, MultipartFile file) throws Exception {
		int ret = fileUploadService.fileUpload(id, file, "barter");
		if (ret > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
