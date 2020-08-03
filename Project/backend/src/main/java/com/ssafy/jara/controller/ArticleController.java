package com.ssafy.jara.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.jara.dto.Article;
import com.ssafy.jara.service.ArticleCommentService;
import com.ssafy.jara.service.ArticleService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/articles")
public class ArticleController {
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	ArticleCommentService articleCommentService;
	
	@ApiOperation(value = "게시글 등록", response = Integer.class)
	@PostMapping("")
	private ResponseEntity<Integer> insertArticle(@RequestBody Article article) {
		
		if(articleService.insertArticle(article) > 0) {
			return new ResponseEntity<Integer>(article.getId(), HttpStatus.OK);
		}
		
		return new ResponseEntity<Integer>(0, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
//	@ApiOperation(value = "전체 게시글 조회", response = List.class)
//	@GetMapping("")
//	private ResponseEntity<List<Article>> selectListTotalArticle() {
//		return new ResponseEntity<List<Article>>(articleService.selectListTotalArticle(), HttpStatus.OK);
//	}
	
//	@ApiOperation(value = "사용자가 작성한 전체 게시글 및 댓글 조회", response = List.class)
//	@GetMapping("/writer/{user_id}")
//	private ResponseEntity<List<Article>> selectListMyArticle(@PathVariable("user_id") int user_id) {
//		List<Article> articleList = articleService.selectListArticle(user_id);
//		
//		for (int i = 0; i < articleList.size(); i++) {
//			Article article = articleList.get(i);
//			article.setComments(articleCommentService.selectArticleComments(article.getId()));
//		}
//		
//		return new ResponseEntity<List<Article>>(articleList, HttpStatus.OK);
//	}
	
	@ApiOperation(value = "사용자가 작성한 전체 게시글 및 사용자가 팔로우하는 다른 사용자의 전체 게시글 및 댓글, 좋아요 사용자 조회", response = List.class)
	@GetMapping("")
	private ResponseEntity<List<Article>> selectListArticle(@RequestParam int user_id) {
		List<Article> articleList = articleService.selectListArticle(user_id);
		
		for (int i = 0; i < articleList.size(); i++) {
			Article article = articleList.get(i);
			
			article.setComments(articleCommentService.selectArticleComments(article.getId())); // 전체 댓글 조회
			article.setLikeAccounts(articleService.selectArticleLikeAccount(article.getId())); // 전체 좋아요 사용자 조회
		}
		
		return new ResponseEntity<List<Article>>(articleList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시글 및 댓글, 좋아요 사용자 조회", response = Article.class)
	@GetMapping("/{id}")
	private ResponseEntity<Article> selectArticle(@PathVariable("id") int id) {
		Article article = articleService.selectArticle(id);
		article.setComments(articleCommentService.selectArticleComments(article.getId()));
		article.setLikeAccounts(articleService.selectArticleLikeAccount(article.getId()));
		
		return new ResponseEntity<Article>(article, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시글 수정", response = String.class)
	@PutMapping("/{id}")
	private ResponseEntity<String> updateArticle(@RequestBody Article article) {
		if(articleService.updateArticle(article) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "게시글 및 게시글 댓글, 좋아요 모두 삭제", response = String.class)
	@DeleteMapping("/{id}")
	private ResponseEntity<String> deleteArticle(@PathVariable("id") int id) {
		if(articleCommentService.selectArticleComments(id).size() > 0) { // 게시글의 댓글 모두 삭제
			articleCommentService.deleteArticleComments(id); 
		}
		
		if(articleService.selectArticleLikes(id) > 0) { // 게시글의 좋아요 모두 삭제
			articleService.deleteArticleLikes(id);
		}
		
		if(articleService.deleteArticle(id) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "게시글 공유 수 수정", response = String.class)
	@PutMapping("/{id}/shares")
	private ResponseEntity<String> updateArticleShares(@PathVariable("id") int id) {
		if(articleService.updateArticleShares(id) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
	}
	
//	@ApiOperation(value = "사용자의 해당 게시글 좋아요 여부 확인", response = Boolean.class)
//	@GetMapping("/{id}/like/{user_id}")
//	private ResponseEntity<Boolean> checkArticleLike(@PathVariable("id") int article_id, @PathVariable("user_id") int user_id) {
//		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
//		hashMap.put("article_id", article_id);
//		hashMap.put("user_id", user_id);
//		
//		return new ResponseEntity<Boolean>(articleService.selectArticleLike(hashMap) > 0, HttpStatus.OK);
//	}
	
	@ApiOperation(value = "게시글 좋아요 등록", response = String.class)
	@PostMapping("/{id}/like")
	private ResponseEntity<String> insertArticleLike(@PathVariable("id") int article_id, @RequestParam("user_id") int user_id) {
		HashMap<String, Integer> hashMap = new HashMap<>();
		hashMap.put("article_id", article_id);
		hashMap.put("user_id", user_id);
		
		if (articleService.insertArticleLike(hashMap) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}

		return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ApiOperation(value = "게시글 좋아요 삭제", response = String.class)
	@DeleteMapping("/{id}/like")
	private ResponseEntity<String> deleteArticleLike(@PathVariable("id") int article_id, @RequestParam("user_id") int user_id) {
		HashMap<String, Integer> hashMap = new HashMap<>();
		hashMap.put("article_id", article_id);
		hashMap.put("user_id", user_id);
		
		if (articleService.deleteArticleLike(hashMap) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}

		return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
