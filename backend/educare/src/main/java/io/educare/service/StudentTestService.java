package io.educare.service;

import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.StudentTestDto;
import io.educare.entity.StudentTest;

public interface StudentTestService {
	
	public boolean insertStudentTest(String username, long testNum);	//강사가 시험에 학생을 추가하면 studenttest table에 학생id, 시험id와 함께 insert
	
	public StudentTest getStudentTest(String username, long testNum);
	
	public boolean updateMyTest(StudentTestDto sttDto, MultipartFile mfile);
	
	public boolean updateTestScore(StudentTestDto sttDto);
}
