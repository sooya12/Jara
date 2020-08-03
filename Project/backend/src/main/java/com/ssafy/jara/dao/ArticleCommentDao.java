package com.ssafy.jara.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.dto.ArticleComment;

@Mapper
public interface ArticleCommentDao {

	public int insertArticleComment(ArticleComment articleComment);
	
	public List<ArticleComment> selectArticleComments(int article_id);
	
	public ArticleComment selectArticleComment(int id);
	
	public int updateArticleComment(ArticleComment articleComment);
	
	public int deleteArticleComment(int id);
	
	public int deleteArticleComments(int article_id);
	
	public int updateArticleCommentInvisible(int id);
	
	public int updateArticleCommentVisible(int id);
}
