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
import io.educare.dto.StudentTestDto;
import io.educare.service.StudentTestService;

@RestController
@RequestMapping("/stutest")
public class StudentTestController {
	private final StudentTestService stTestservice;
	public StudentTestController(StudentTestService stTestservice) {
		this.stTestservice = stTestservice;
	}
  
	@PostMapping("/insert/{username}/{testnum}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<String> insertStudentTestProblem(@PathVariable String username, @PathVariable long testnum) {	//강사가 테스트에 학생 추가하는 메서드
		if (stTestservice.insertStudentTest(username,testnum)) {
			return new ResponseEntity<String>("학생 시험 매칭 등록 성공", HttpStatus.CREATED);
		} else {
			return  new ResponseEntity<String>("학생 시험 매칭 등록 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get/{username}/{testnum}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR','STUDENT')")
	public ResponseEntity<StudentTestDto> getStudentTest(@PathVariable String username, @PathVariable long testnum) {
		return new ResponseEntity<StudentTestDto>(stTestservice.getStudentTest(username,testnum), HttpStatus.OK) ;
	}
	
	// 학생이 자신이 참여할 시험 리스트 조회
	@GetMapping("/get/{username}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR','STUDENT')")
	public ResponseEntity<List<StudentTestDto>> getStudentTestListByUname(@PathVariable String username) {
		return new ResponseEntity<List<StudentTestDto>>(stTestservice.getStudentTestListByUname(username), HttpStatus.OK) ;
	}
	
	// 강사가 studentTest에서 자기 출제한 test pk 주고 시험에 등록한 학생 응시자들 조회
	@GetMapping("/getstu/{testnum}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<List<StudentTestDto>> getStudentTestListByTNum(@PathVariable long testnum) {
		return new ResponseEntity<List<StudentTestDto>>(stTestservice.getStudentTestListByTNum(testnum), HttpStatus.OK) ;
	}
	
	@PutMapping("/update-mytest")
	//@PreAuthorize("hasAnyRole('STUDENT')")	//답안 등록은 학생들만 가능
	public ResponseEntity<String> updateStudentTest(StudentTestDto sttDto,
			@RequestParam(value = "file", required = false) MultipartFile mfile) {	//학생이 시험답안, 영상 제출할때 request 처리메소드
		
		System.out.println("updatemyTestCONTROLLER"+sttDto.getUsername());
		System.out.println("updatemyTestCONTROLLER"+mfile);
				
		if (mfile != null && stTestservice.updateMyTest(sttDto, mfile)) {
			return new ResponseEntity<String>("답안 작성, 녹화파일 저장 성공", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("답안 작성, 녹화파일 저장 실패", HttpStatus.NOT_MODIFIED);
		}
	}
  
	@PutMapping("/update-score/{username}/{testnum}/{testResult}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<String> updateScore(@PathVariable String username,@PathVariable Long testnum,@PathVariable String testResult) {
		
		if (stTestservice.updateTestScore(username, testnum, testResult)) {
			return  new ResponseEntity<String>("채점 결과 저장 성공",  HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("채점 결과 저장 실패",  HttpStatus.NOT_MODIFIED);		
		}
	}
	
	@DeleteMapping("/delete/{username}/{testnum}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<String> deleteStudentTestByUserName(@PathVariable String username, @PathVariable long testnum) {
		
		if (stTestservice.deleteStudentTestByUserName(username, testnum)) {
			return  new ResponseEntity<String>("유저 시험정보 삭제 성공",  HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<String>("유저 시험정보 삭제 실패",  HttpStatus.INTERNAL_SERVER_ERROR);		
		}
	}

}