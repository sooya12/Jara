package com.ssafy.jara.service;

import java.util.List;

import com.ssafy.jara.dto.ArticleComment;

public interface ArticleCommentService {

	public int insertArticleComment(ArticleComment articleComment);
	
	public List<ArticleComment> selectArticleComments(int article_id);
	
	public ArticleComment selectArticleComment(int id);
	
	public int updateArticleComment(ArticleComment articleComment);
	
	public int deleteArticleComment(int id);
	
	public int deleteArticleComments(int article_id);
	
	public int updateArticleCommentInvisible(int id);
	
	public int updateArticleCommentVisible(int id);
}
