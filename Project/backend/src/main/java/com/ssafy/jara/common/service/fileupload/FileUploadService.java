package com.ssafy.jara.common.service.fileupload;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
	public int fileUpload(int id, MultipartFile file, String boardType) throws Exception;
}
