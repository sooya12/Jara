package com.ssafy.jara.common.dao.fileupload;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.common.dto.fileupload.ArticleFileUpload;
import com.ssafy.jara.common.dto.fileupload.BarterFileUpload;
import com.ssafy.jara.common.dto.fileupload.TipFileUpload;

@Mapper
public interface FileUploadDao {
	public int insertArticleFile(ArticleFileUpload articleFileUpload) throws Exception;
	public int insertTipFile(TipFileUpload tipFileUpload) throws Exception;
	public int insertBarterFile(BarterFileUpload barterFileUpload) throws Exception;
}
