package io.educare.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.educare.dto.TestDto;
import io.educare.entity.Test;
import io.educare.service.TestService;

@RestController
@RequestMapping("/test")
public class TestController {

private final TestService testService;
	
	public TestController(TestService testService) {
		this.testService = testService;
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<Boolean> insertTest(@RequestParam String username, @RequestBody Test test) {
		Boolean check = testService.insertTest(username, test);	
		return new ResponseEntity<Boolean>(check, HttpStatus.CREATED);
	}
	
	@GetMapping("/get")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<List<TestDto>> getTestsByUsername(@RequestParam String username) {
		List<TestDto> tests = testService.getTestsByUsername(username);
		return new ResponseEntity<List<TestDto>>(tests, HttpStatus.OK);
	}
	
	@GetMapping("/get/{testnum}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<TestDto> getTest(@PathVariable Long testnum) {
		TestDto testDto = testService.getTest(testnum);
		return new ResponseEntity<TestDto>(testDto, HttpStatus.OK);
	}
	
	@PostMapping("/update")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<Boolean> updateTest(@RequestBody Test test) {
		Boolean check = testService.updateTest(test);	
		return new ResponseEntity<Boolean>(check, HttpStatus.CREATED);
	}
	@DeleteMapping("/delete")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<Boolean> deleteTest(@RequestParam String username, @RequestParam Long testnum) {
		Boolean check = testService.deleteTest(username, testnum);
		return new ResponseEntity<Boolean>(check, HttpStatus.NO_CONTENT);
	}
	
}
