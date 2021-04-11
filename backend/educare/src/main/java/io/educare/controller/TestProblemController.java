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
	public ResponseEntity<Boolean> insertTestProblem(@PathVariable long testnum, TestProblemDto tProblemDto, 
			@RequestParam(value = "file", required = false) MultipartFile mfile) {
		
		Boolean check = null;
		
		if (mfile != null) {
			check = tProblemService.insertTProblem(testnum, tProblemDto, mfile);
		} else {
			check = tProblemService.insertTProblemNoimg(testnum, tProblemDto);
		}
		return new ResponseEntity<Boolean>(check, HttpStatus.CREATED);
	}
	
	@GetMapping("/get")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<List<TestProblemDto>> getTProblemsByTNum(@RequestParam long testnum) {
		return new ResponseEntity<List<TestProblemDto>>(tProblemService.getTProblemsByTNum(testnum), HttpStatus.OK);
	}
	
	@GetMapping("/get/{proid}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<TestProblemDto> getTestProblem(@PathVariable long proid) {
		return new ResponseEntity<TestProblemDto>(tProblemService.getTProblem(proid), HttpStatus.OK);
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<Boolean> updateTestProblem(TestProblemDto tProblemDto, 
			@RequestParam(value = "file", required = false) MultipartFile mfile) {
		
		Boolean check = null;
		if (mfile != null) {
			check = tProblemService.updateTProblem(tProblemDto, mfile);
		} else {
			check = tProblemService.updateTProblemNoimg(tProblemDto);
		}
		return new ResponseEntity<Boolean>(check, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<Boolean> deleteTestProblem(@RequestParam long proid, @RequestParam long testnum) {
		return new ResponseEntity<Boolean>(tProblemService.deleteTProblem(proid, testnum), HttpStatus.NO_CONTENT);
	}
}
