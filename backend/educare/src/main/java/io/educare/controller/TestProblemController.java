package io.educare.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.TestProblemDto;
import io.educare.service.TestProblemService;

@RestController
@RequestMapping("/testpro")
public class TestProblemController {
	
	private final TestProblemService tProblemService;
	
	public TestProblemController(TestProblemService tProblemService) {
		this.tProblemService = tProblemService;
	}
	
	@PostMapping("/create/{testnum}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<String> insertTestProblem(@PathVariable long testnum, TestProblemDto tProblemDto, 
			@RequestParam(value = "file", required = false) MultipartFile mfile) {
		Boolean check = null;
		if (mfile != null) {
			check = tProblemService.insertTProblem(testnum, tProblemDto, mfile);
		} else {
			check = tProblemService.insertTProblemNoimg(testnum, tProblemDto);
		}
		
		if (check) {
			return new ResponseEntity<String>("시험 문제 생성 성공", HttpStatus.CREATED);			
		} else {
			return new ResponseEntity<String>("시험 문제 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	@GetMapping("/get")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<List<TestProblemDto>> getTestProblemsByTNum(@RequestParam long testnum) {
		return new ResponseEntity<List<TestProblemDto>>(tProblemService.getTProblemsByTNum(testnum), HttpStatus.OK);
	}
	
	@GetMapping("/get/{proid}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<TestProblemDto> getTestProblem(@PathVariable long proid) {
		return new ResponseEntity<TestProblemDto>(tProblemService.getTProblem(proid), HttpStatus.OK);
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<String> updateTestProblem(TestProblemDto tProblemDto, 
			@RequestParam(value = "file", required = false) MultipartFile mfile) {
		Boolean check = null;
		if (mfile != null) {
			check = tProblemService.updateTProblem(tProblemDto, mfile);
		} else {
			check = tProblemService.updateTProblemNoimg(tProblemDto);
		}
		
		if (check) {
			return new ResponseEntity<String>("시험 문제 수정 성공", HttpStatus.OK);			
		} else {
			return new ResponseEntity<String>("시험 문제 수정 실패", HttpStatus.NOT_MODIFIED);			
		}
	}
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<String> deleteTestProblem(@RequestParam long proid, @RequestParam long testnum) {
		
		if (tProblemService.deleteTProblem(proid, testnum)) {
			return new ResponseEntity<String>("시험 문제 삭제 성공", HttpStatus.NO_CONTENT);			
		} else {
			return new ResponseEntity<String>("시험 문제 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
}