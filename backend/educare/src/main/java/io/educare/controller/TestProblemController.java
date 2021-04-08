package io.educare.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.TestProblemDto;
import io.educare.entity.TestProblem;
import io.educare.service.TestProblemService;

@RestController
@RequestMapping("/testpro")
public class TestProblemController {
	
	private final TestProblemService tProblemService;
	
	public TestProblemController(TestProblemService tProblemService) {
		this.tProblemService = tProblemService;
	}
	
	@PostMapping("/create/{strtnum}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<Boolean> insertTestProblem(@PathVariable String strtnum, TestProblem tProblem, 
			@RequestParam(value = "file", required = false) MultipartFile mfile) {
		
		Long testnum = Long.valueOf(strtnum);
		Boolean check = null;
		
		if (mfile != null) {
			check = tProblemService.insertTProblem(testnum, tProblem, mfile);
		} else {
			check = tProblemService.insertTProblemNoimg(testnum, tProblem);
		}
		return new ResponseEntity<Boolean>(check, HttpStatus.CREATED);
	}
	
	@GetMapping("/get")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<List<TestProblemDto>> getTProblemsByTNum(Long testnum) {
		return new ResponseEntity<List<TestProblemDto>>(tProblemService.getTProblemsByTNum(testnum), HttpStatus.OK);
	}
	
	@GetMapping("/get/{pronum}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<TestProblemDto> getTestProblem(@PathVariable Long pronum) {
		return new ResponseEntity<TestProblemDto>(tProblemService.getTProblem(pronum), HttpStatus.OK);
	}
	
	@PostMapping("/update")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<Boolean> updateTestProblem(TestProblem tProblem, 
			@RequestParam(value = "file", required = false) MultipartFile mfile) {
		
		Boolean check = null;
		if (mfile != null) {
			check = tProblemService.updateTProblem(tProblem, mfile);
		} else {
			check = tProblemService.updateTProblemNoimg(tProblem);
		}
		return new ResponseEntity<Boolean>(check, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<Boolean> deleteUser(@RequestParam Long pronum, @RequestParam Long testnum) {
		return new ResponseEntity<Boolean>(tProblemService.deleteTProblem(pronum, testnum), HttpStatus.NO_CONTENT);
	}
}
