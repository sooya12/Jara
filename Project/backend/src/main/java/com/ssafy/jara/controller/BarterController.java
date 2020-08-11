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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.jara.common.service.fileupload.FileUploadService;
import com.ssafy.jara.dto.Barter;
import com.ssafy.jara.service.BarterService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/jara/barters")
public class BarterController {
	
	@Autowired
	BarterService barterService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@ApiOperation(value = "새로운 물물교환 등록", response = String.class)
	@PostMapping("")
	private ResponseEntity<Barter> insertBarter(@RequestBody Barter barter) {
		int ret = barterService.insertBarter(barter);
		if (ret > 0) {
			int barterId = barter.getId();
			Barter newBarter = barterService.selectBarter(barterId);
			return new ResponseEntity<Barter>(newBarter, HttpStatus.OK);
		} else {
			return new ResponseEntity<Barter>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "해당 물물교환 조회", response = String.class)
	@GetMapping("/{id}")
	private ResponseEntity<Barter> selectBarter(@PathVariable int id) {
		Barter barter = barterService.selectBarter(id);
		if (barter != null) {
			barter.setStored_file_name(fileUploadService.selectBarterFileName(id));
			return new ResponseEntity<Barter>(barter, HttpStatus.OK);
		} else {
			return new ResponseEntity<Barter>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "해당 물물교환 수정 (title, price, contents, status)", response = String.class)
	@PutMapping("/{id}")
	private ResponseEntity<Barter> updateBarter(@PathVariable int id, @RequestBody Barter barter) {
		int ret = barterService.updateBarter(barter);
		if (ret > 0) {
			Barter updatedBarter = barterService.selectBarter(id);
			return new ResponseEntity<Barter>(updatedBarter, HttpStatus.OK);
		} else {
			return new ResponseEntity<Barter>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "물물교환 삭제", response = String.class)
	@DeleteMapping("/{id}")
	private ResponseEntity<String> deleteBarter(@PathVariable int id) {
		if (barterService.deleteBarter(id) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "전체 물물교환 조회", response = String.class)
	@GetMapping("")
	private ResponseEntity<List<Barter>> selectListBarter() {
		return new ResponseEntity<List<Barter>>(barterService.selectListBarter(), HttpStatus.OK);
	}
	
//	@ApiOperation(value = "태그로 물물교환 조회", response = String.class)
//	@GetMapping("/tag")
//	private ResponseEntity<List<Barter>> selectListBarterTag(@RequestBody int tag_id) {
//		return new ResponseEntity<List<Barter>>(barterService.selectListBarterTag(tag_id), HttpStatus.OK);
//	}
	
	@ApiOperation(value = "구해요 태그 물물교환 조회", response = String.class)
	@GetMapping("/want")
	private ResponseEntity<List<Barter>> selectListBarterWant() {
		return new ResponseEntity<List<Barter>>(barterService.selectListBarterTag(5), HttpStatus.OK);
	}
	
	@ApiOperation(value = "사요 태그 물물교환 조회", response = String.class)
	@GetMapping("/buy")
	private ResponseEntity<List<Barter>> selectListBarterBuy() {
		return new ResponseEntity<List<Barter>>(barterService.selectListBarterTag(6), HttpStatus.OK);
	}
	
	@ApiOperation(value = "팔아요 태그 물물교환 조회", response = String.class)
	@GetMapping("/sell")
	private ResponseEntity<List<Barter>> selectListBarterSell() {
		return new ResponseEntity<List<Barter>>(barterService.selectListBarterTag(7), HttpStatus.OK);
	}
	
	@ApiOperation(value = "나눠요 태그 물물교환 조회", response = String.class)
	@GetMapping("/share")
	private ResponseEntity<List<Barter>> selectListBarterShare() {
		return new ResponseEntity<List<Barter>>(barterService.selectListBarterTag(8), HttpStatus.OK);
	}
	
	@ApiOperation(value = "검색어로 물물교환 조회", response = String.class)
	@GetMapping("/search")
	private ResponseEntity<List<Barter>> selectListBarterSearch(@RequestParam String searchWord) {
		return new ResponseEntity<List<Barter>>(barterService.selectListBarterSearch(searchWord), HttpStatus.OK);
	}
	
	@ApiOperation(value = "해당 물물교환 조회수 증가", response = String.class)
	@PutMapping("/{id}/hits")
	private ResponseEntity<String> updateBarterHits(@PathVariable int id) {
		if (barterService.updateBarterHits(id) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
