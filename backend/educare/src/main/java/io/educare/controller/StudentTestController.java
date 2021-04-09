package io.educare.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.StudentTestDto;
import io.educare.entity.StudentTest;
import io.educare.service.StudentTestService;

@RestController
@RequestMapping("/studenttest")
public class StudentTestController {
	private final StudentTestService stTestservice;

	public StudentTestController(StudentTestService stTestservice) {
		this.stTestservice = stTestservice;
	}

	@PutMapping("/insert/{username}/{testnum}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public String insertStudentTestProblem(@PathVariable String username, @PathVariable long testnum) {	//강사가 테스트에 학생 추가하는 메서드

		if (stTestservice.insertStudentTest(username,testnum)) {
			return "학생 시험 매칭 등록 성공";
		} else {
			return "학생 시험 매칭 등록 실패";
		}
	}

	@GetMapping("/get/{username}/{testnum}")
	@PreAuthorize("hasAnyRole('INSTRUCTOR','SUTDENT')")
	public StudentTest getStudentTest(@PathVariable String username, @PathVariable long testnum) {	//studenttest 객체 반환하는 메서드

		return stTestservice.getStudentTest(username,testnum);
	}

	@PostMapping("/update-mytest")
	@PreAuthorize("hasAnyRole('SUTDENT')")	//답안 등록은 학생들만 가능
	public String updateStudentTest(StudentTestDto sttDto, 
			@RequestParam(value = "file", required = false) MultipartFile mfile) {	//학생이 시험답안, 영상 제출할때 request 처리메소드

		if (mfile != null && stTestservice.updateMyTest(sttDto, mfile)) {
			return "답안 작성, 녹화파일 저장 성공";

		}else {
			return "답안 작성, 녹화파일 저장 실패";
		}
	}

	@PostMapping("/update-score")
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public String updateScore(@RequestBody StudentTestDto sttDto) {
		
		if (stTestservice.updateTestScore(sttDto)) {
			return "채점 결과 저장 성공";
		} else {
			return "채점 결과 저장 실패";			
		}
	}
}
