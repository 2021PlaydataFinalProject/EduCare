//package io.educare.controller;
//
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import io.educare.entity.Test;
//import io.educare.entity.TestProblem;
//import io.educare.service.TestService;
//
//@RestController
//@RequestMapping("/test")
//public class TestController {
//
//private final TestService testService;
//	
//	public TestController(TestService testService) {
//		this.testService = testService;
//	}
//	
//	@PostMapping("/create")
//	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
//	public ResponseEntity<Test> insertTest(String username, Test test) {
//		
//		Test savedTest = testService.insertTest(username, test);
//		
//		return new ResponseEntity<Test>(savedtest, HttpStatus.CREATED);
//	}
//	
//	@GetMapping("/get")
//	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
//	public ResponseEntity<List<Test>> getTestsByUsername(String username) {
//		List<Test> tests = testService.getTestsByUsername(username);
//		return new ResponseEntity<List<Test>>(tests, HttpStatus.OK);
//	}
//	
//	@GetMapping("/get/{testnum}")
//	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
//	public ResponseEntity<Test> getTest(@PathVariable Long testnum) {
//		Test test = testService.getTest(testnum);
//		return new ResponseEntity<Test>(test, HttpStatus.OK);
//	}
//	
//	@PostMapping("/update")
//	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
//	public ResponseEntity<Test> updateTest(@RequestBody Test test) {
//		
//		Test updatedTest = testService.updateTest(test);
//		
//		return new ResponseEntity<Test>(updatedTest, HttpStatus.CREATED);
//	}
//	
//}
