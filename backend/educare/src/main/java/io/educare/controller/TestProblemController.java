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

import io.educare.entity.TestProblem;
import io.educare.service.TestProblemService;
import io.educare.service.TestService;

@RestController
@RequestMapping("/testpro")
public class TestProblemController {
	
	private final TestProblemService tProblemService;
	
	public TestProblemController(TestProblemService tProblemService) {
		this.tProblemService = tProblemService;
	}
	
	@PostMapping("/create/{strtnum}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<TestProblem> insertTestProblem(@PathVariable String strtnum, TestProblem tProblem, 
			@RequestParam(value = "file", required = false) MultipartFile mfile) {
		
		Long testnum = Long.valueOf(strtnum);
		
		TestProblem testProblem = null;
		if (mfile != null) {
			testProblem = tProblemService.insertTProblem(testnum, tProblem, mfile);
		} else {
			testProblem = tProblemService.insertTProblemNoimg(testnum, tProblem);
		}
		return new ResponseEntity<TestProblem>(testProblem, HttpStatus.CREATED);
	}
	
	@GetMapping("/get")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<List<TestProblem>> getTProblemsByTNum(Long testnum) {
		List<TestProblem> tproblems = tProblemService.getTProblemsByTNum(testnum);
		return new ResponseEntity<List<TestProblem>>(tproblems, HttpStatus.OK);
	}
	
	@GetMapping("/get/{pronum}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<TestProblem> getTestProblem(@PathVariable Long pronum) {
		TestProblem tproblem = tProblemService.getTProblem(pronum);
		return new ResponseEntity<TestProblem>(tproblem, HttpStatus.OK);
	}
	
	@PostMapping("/update")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<TestProblem> updateTestProblem(TestProblem tProblem, 
			@RequestParam(value = "file", required = false) MultipartFile mfile) {
		
		TestProblem testProblem = null;
		if (mfile != null) {
			testProblem = tProblemService.updateTProblem(tProblem, mfile);
		} else {
			testProblem = tProblemService.updateTProblemNoimg(tProblem);
		}
		return new ResponseEntity<TestProblem>(testProblem, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<HttpStatus> deleteUser(@RequestParam Long pronum) {
		tProblemService.deleteTProblem(pronum);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
