package io.educare.service;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import io.educare.dto.StudentTestDto;
public interface StudentTestService {
	
	public boolean insertStudentTest(String username, long testNum);	//강사가 시험에 학생을 추가하면 studenttest table에 학생id, 시험id와 함께 insert
	
	public StudentTestDto getStudentTest(String username, long testNum);
	
	public List<StudentTestDto> getStudentTestListByUname(String username);
	
	public List<StudentTestDto> getStudentTestListByTNum(long testNum);
	
	public boolean updateMyTest(StudentTestDto sttDto, MultipartFile mfile);
	
	public boolean updateTestScore(String username, long testnum, String testresult);
	
	public boolean deleteStudentTestByUserName(String username, long testnum);
}





