package com.ssafy.jara.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.dto.Article;

@Mapper
public interface ArticleDao {
	
	public int insertArticle(Article article);
	
	public List<Article> selectListMyArticle(int writer);
	
	public List<Article> selectListArticle(int writer);
	
	public List<Article> selectRangeListArticle(HashMap<String, Integer> hashMap);
	
	public Article selectArticle(int id);
	
	public int updateArticle(Article article);
	
	public int deleteArticle(int id);
	
	public int updateArticleShares(int id);
	
	public int insertArticleLike(HashMap<String, Integer> hashMap);
	
	public int selectArticleLike(HashMap<String, Integer> hashMap);
	
	public int selectArticleLikes(int article_id);
	
	public int deleteArticleLike(HashMap<String, Integer> hashMap);
	
	public int deleteArticleLikes(int article_id);
	
	public List<Integer> selectArticleLikeAccount(int article_id);

}
