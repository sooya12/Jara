package com.ssafy.jara.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.jara.dto.TipComment;
import com.ssafy.jara.service.TipCommentService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/tips")
public class TipCommentController {

	@Autowired
	TipCommentService tipCommentService;
	
	@ApiOperation(value = "팁 댓글 등록", response = String.class)
	@PostMapping("/{tip_id}/comments")
	private ResponseEntity<TipComment> insertTipComment(@PathVariable("tip_id") int tip_id, @RequestBody TipComment tipComment) {
		tipComment.setTip_id(tip_id);
		
		if(tipCommentService.insertTipComment(tipComment) > 0) {
			return new ResponseEntity<TipComment>(tipCommentService.selectTipComment(tipComment.getId()), HttpStatus.OK);
		}
		
		return new ResponseEntity<TipComment>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ApiOperation(value = "팁 전체 댓글 조회", response = List.class)
	@GetMapping("/{tip_id}/comments")
	private ResponseEntity<List<TipComment>> selectTipComments(@PathVariable("tip_id") int tip_id) {
		return new ResponseEntity<List<TipComment>>(tipCommentService.selectTipComments(tip_id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "팁 댓글 조회", response = TipComment.class)
	@GetMapping("/{tip_id}/comments/{id}")
	private ResponseEntity<TipComment> selectTipComment(@PathVariable("id") int id) {
		return new ResponseEntity<TipComment>(tipCommentService.selectTipComment(id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "팁 댓글 수정", response = String.class)
	@PutMapping("/{tip_id}/comments/{id}")
	private ResponseEntity<String> updateTipComment(@RequestBody TipComment tipComment) {
		if(tipCommentService.updateTipComment(tipComment) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "팁 댓글 삭제", response = String.class)
	@DeleteMapping("/{tip_id}/comments/{id}")
	private ResponseEntity<String> deleteTipComment(@PathVariable("id") int id) {
		if(tipCommentService.deleteTipComment(id) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
	}
	
}
