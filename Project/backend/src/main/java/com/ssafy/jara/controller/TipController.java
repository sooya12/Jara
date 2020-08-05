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

import com.ssafy.jara.dto.Tip;
import com.ssafy.jara.service.TipCommentService;
import com.ssafy.jara.service.TipService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/tips")
public class TipController {

	@Autowired
	TipService tipService;
	
	@Autowired
	TipCommentService tipCommentService;
	
	@ApiOperation(value = "팁 등록", response = String.class)
	@PostMapping("")
	private ResponseEntity<Integer> insertTip(@RequestBody Tip tip) {
		if(tipService.insertTip(tip) > 0) {
			return new ResponseEntity<Integer>(tip.getId(), HttpStatus.OK);
		} 
		
		return new ResponseEntity<Integer>(0, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ApiOperation(value = "전체 팁 조회", response = List.class)
	@GetMapping("")
	private ResponseEntity<List<Tip>> selectListTip() {
		return new ResponseEntity<List<Tip>>(tipService.selectListTip(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "요리 태그 팁 조회", response = List.class)
	@GetMapping("/cook")
	private ResponseEntity<List<Tip>> selectListTipCook() {
		return new ResponseEntity<List<Tip>>(tipService.selectListTipTag(1), HttpStatus.OK);
	}
	
	@ApiOperation(value = "세탁 태그 팁 조회", response = List.class)
	@GetMapping("/laundry")
	private ResponseEntity<List<Tip>> selectListTipLaundry() {
		return new ResponseEntity<List<Tip>>(tipService.selectListTipTag(2), HttpStatus.OK);
	}
	
	@ApiOperation(value = "청소 태그 팁 조회", response = List.class)
	@GetMapping("/clean")
	private ResponseEntity<List<Tip>> selectListTipClean() {
		return new ResponseEntity<List<Tip>>(tipService.selectListTipTag(3), HttpStatus.OK);
	}
	
	@ApiOperation(value = "보관 태그 팁 조회", response = List.class)
	@GetMapping("/neat")
	private ResponseEntity<List<Tip>> selectListTipNeat() {
		return new ResponseEntity<List<Tip>>(tipService.selectListTipTag(4), HttpStatus.OK);
	}
	
	@ApiOperation(value = "검색어에 해당하는 전체 팁 조회", response = List.class)
	@GetMapping("/search/{searchWord}")
	private ResponseEntity<List<Tip>> selectListTipSearch(@PathVariable("searchWord") String searchWord) {
		return new ResponseEntity<List<Tip>>(tipService.selectListTipSearch(searchWord), HttpStatus.OK);
	}
	
	@ApiOperation(value = "태그와 검색어에 해당하는 전체 팁 조회", response = List.class) 
	@GetMapping("/search/{searchWord}/{tag_id}")
	private ResponseEntity<List<Tip>> selectListTipTagSearch(@PathVariable("searchWord") String searchWord, @PathVariable("tag_id") int tag_id) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("searchWord", searchWord);
		hashMap.put("tag_id", String.valueOf(tag_id));
		
		return new ResponseEntity<List<Tip>>(tipService.selectListTipTagSearch(hashMap), HttpStatus.OK);
	}
	
	@ApiOperation(value = "해당 팁 조회", response = Tip.class)
	@GetMapping("/{id}")
	private ResponseEntity<Tip> selectTip(@PathVariable("id") int id) {
		Tip tip = tipService.selectTip(id);
		tip.setComments(tipCommentService.selectTipComments(id));
		tip.setLikeAccounts(tipService.selectTipLikeAccounts(id));
		
		return new ResponseEntity<Tip>(tip, HttpStatus.OK);
	}
	
	@ApiOperation(value = "팁 제목/내용 수정", response = String.class)
	@PutMapping("/{id}")
	private ResponseEntity<String> updateTip(@PathVariable("id") int id, @RequestBody Tip tip) {
		if(tipService.updateTip(tip) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "팁 조회수 수정", response = String.class)
	@PutMapping("/{id}/hits")
	private ResponseEntity<String> updateTipHits(@PathVariable("id") int id) {
		if(tipService.updateTipHits(id) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ApiOperation(value = "팁 삭제", response = String.class)
	@DeleteMapping("/{id}")
	private ResponseEntity<String> deleteTip(@PathVariable("id") int id) {
		if(tipCommentService.selectTipComments(id).size() > 0) {
			tipCommentService.deleteTipComments(id);
		}
		
		if(tipService.selectTip(id).getLikes() > 0) {
			tipService.deleteTipLikes(id);
		}
		
		if(tipService.deleteTip(id) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "해당 사용자의 팁 좋아요 여부 확인", response = Boolean.class)
	@GetMapping("/{id}/like/{user_id}")
	private ResponseEntity<Boolean> checkTipLike(@PathVariable("id") int tip_id, @PathVariable("user_id") int user_id) {
		HashMap<String, Integer> hashMap = new HashMap<>();
		hashMap.put("tip_id", tip_id);
		hashMap.put("user_id", user_id);
		
		return new ResponseEntity<Boolean>(tipService.selectTipLike(hashMap) > 0, HttpStatus.OK);
	}
	
	@ApiOperation(value = "팁 좋아요 여부 확인 후 좋아요 삭제/등록", response = String.class)
	@PostMapping("/{id}/like")
	private ResponseEntity<String> setTipLike(@PathVariable("id") int tip_id, @RequestParam("user_id") int user_id) {
		HashMap<String, Integer> hashMap = new HashMap<>();
		hashMap.put("tip_id", tip_id);
		hashMap.put("user_id", user_id);
		
		if(tipService.selectTipLike(hashMap) > 0) { // 사용자가 해당 글에 좋아요를 이미 누른 경우 - 좋아요 삭제
			if(tipService.deleteTipLike(hashMap) > 0) { 
				return new ResponseEntity<String>("success", HttpStatus.OK);
			}
			
			return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
		
		} else { // 사용자가 해당 글에 좋아요를 누르지 않은 경우 - 좋아요 등록
			if(tipService.insertTipLike(hashMap) > 0) {
				return new ResponseEntity<String>("success", HttpStatus.OK);
			}
			
			return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "팁 스크랩 등록", response = String.class)
	@PostMapping("/{id}/scrap")
	private ResponseEntity<String> insertTipScrap(@PathVariable("id") int tip_id, @RequestBody int user_id) {
		HashMap<String, Integer> hashMap = new HashMap<>();
		hashMap.put("tip_id", tip_id);
		hashMap.put("user_id", user_id);
		
		if(tipService.insertTipScrap(hashMap) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
