package com.ssafy.jara.common.service.fileupload;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
	public int fileUpload(String message, MultipartFile file) throws Exception;
}
