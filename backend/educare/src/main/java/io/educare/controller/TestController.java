
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.educare.dto.TestDto;
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
	public ResponseEntity<Long> insertTest(@RequestParam String username, @RequestBody TestDto testDto) {
		return new ResponseEntity<Long>(testService.insertTest(username, testDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/get")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<List<TestDto>> getTestsByUsername(@RequestParam String username) {
		return new ResponseEntity<List<TestDto>>(testService.getTestsByUsername(username), HttpStatus.OK);
	}
	
	@GetMapping("/get/{testnum}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<TestDto> getTest(@PathVariable Long testnum) {
		return new ResponseEntity<TestDto>(testService.getTest(testnum), HttpStatus.OK);
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<Boolean> updateTest(@RequestBody TestDto testDto) { 
		return new ResponseEntity<Boolean>(testService.updateTest(testDto), HttpStatus.CREATED);
	}
	@DeleteMapping("/delete")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<Boolean> deleteTest(@RequestParam String username, @RequestParam Long testnum) {
		return new ResponseEntity<Boolean>(testService.deleteTest(username, testnum), HttpStatus.NO_CONTENT);
	}
	
}
