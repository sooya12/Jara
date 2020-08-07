package com.ssafy.jara.service;

import java.util.HashMap;
import java.util.List;

import com.ssafy.jara.dto.Article;

public interface ArticleService {

	public int insertArticle(Article article);
	
	public List<Article> selectListMyArticle(int writer);
	
	public List<Article> selectListArticle(int user_id);
	
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
