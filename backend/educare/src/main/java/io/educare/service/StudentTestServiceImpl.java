package io.educare.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.StudentTestDto;
import io.educare.dto.UserDto;
import io.educare.entity.Student;
import io.educare.entity.StudentTest;
import io.educare.entity.Test;
import io.educare.entity.User;
import io.educare.repository.StudentRepository;
import io.educare.repository.StudentTestRepository;
import io.educare.repository.TestRepository;

@Service
public class StudentTestServiceImpl implements StudentTestService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final TestRepository testRepository;
	private final StudentRepository studentRepository;
	private final StudentTestRepository studentTestRepository;
	
	public StudentTestServiceImpl(TestRepository testRepository, StudentRepository studentRepository,StudentTestRepository studentTestRepository) {
		this.testRepository = testRepository;
		this.studentRepository = studentRepository;
		this.studentTestRepository = studentTestRepository;
	}
	
	@Transactional
	public boolean insertStudentTest(String username, long testNum) {	//강사가 시험에 학생을 추가하면 studenttest table에 학생id, 시험id와 함께 insert
		Optional<Student> stOpt; 
		Optional<Test> testOpt; 
		
		try {
			stOpt = studentRepository.findById(username);
			testOpt = testRepository.findById(testNum);
			
			if (stOpt.isPresent() && testOpt.isPresent()) {
				StudentTest studentTest = new StudentTest(); 
				Student st = stOpt.get();
				Test test = testOpt.get();
				
				studentTest.setStuId(st);
				studentTest.setTestNum(test);
				
				if(studentTestRepository.save(studentTest) == null) {
					logger.info("{} 학생 시험 매칭 등록 실패");
					return false;
				} else {
					logger.info("{} 학생 시험 매칭 등록 성공", studentTest.getStTestNum());
					return true;
				}
			} else {
				logger.info("{} 학생 시험 매칭 등록 실패");
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("{} 학생 시험 매칭 등록 실패");
			return false;
		}
		
	}
	
	public StudentTestDto getStudentTest(String username, long testNum) {
		
		try {
			Optional<StudentTest> stTestOpt = studentTestRepository.findStudentTestByUserNameAndTestNum(username,testNum);
			
			if(stTestOpt.isPresent()) {
				StudentTest stTest = stTestOpt.get();
			
				List<String> cheatTimes = Arrays.asList(stTest.getCheatTime().split(","));
				List<String> testAnswers = Arrays.asList(stTest.getTestAnswer().split(","));
				
				StudentTestDto stuTestDto = StudentTestDto.builder().userRealName(studentRepository.findById(username).get().getUserRealName())
				.username(username).testName(testRepository.findById(testNum).get().getTestName()).testNum(testNum)
				.isCheating(stTest.getIsCheating()).cheatTime(cheatTimes).videoName(stTest.getVideoName()).testAnswer(testAnswers)
				.testStatus(stTest.getTestStatus()).testResult(stTest.getTestResult()).build();
				
				logger.info("{} 학생 {} 시험 정보 조회 성공", username, testNum);
				return stuTestDto;
			} else {
				logger.info("{} 학생 {} 시험 정보 조회 실패", username, testNum);
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("{} 학생 {} 시험 정보 조회 실패", username, testNum);
			return null;
		}		
	}
	
	public List<StudentTestDto> getStudentTestList(String username) {
		Optional<List<StudentTest>> stuTestList = studentTestRepository.findAllStudentTestByUserName(username);
		List<StudentTestDto> stuTestDtoList = stuTestList.get().stream().map(st -> new StudentTestDto( username, null, testRepository.findById(st.getTestNum().getTestNum()).get().getTestName(), 
				st.getTestResult(), st.getIsCheating(), Arrays.asList(st.getCheatTime().split(",")), st.getVideoName(),  Arrays.asList(st.getTestAnswer().split(",")), st.getTestStatus(), st.getTestNum().getTestNum() ))
				.collect(Collectors.toList());
	
		return stuTestDtoList;
	}
	
	@Transactional
	public boolean updateMyTest(StudentTestDto sttDto, MultipartFile mfile) {	//학생이 answer, cheattime, ischeating,   studenttest table update
		Optional<StudentTest> sttOpt = studentTestRepository.findById(sttDto.getTestNum());
		
		if (sttOpt.isPresent()) {
			StudentTest stt = sttOpt.get();
			String videoname;
			
			try {
				videoname = sttDto.getTestNum()+"video.avi";	
				mfile.transferTo(new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\tproblemvideo\\" + videoname));
				logger.info("{}번 문제 녹화파일 등록 성공", sttDto.getTestNum());
				stt.setVideoName(videoname);	//db에 video파일명 저장
				
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				logger.info("{}번 문제 녹화파일 등록 실패", sttDto.getTestNum());
				return false;
			}			

			stt.setTestStatus(sttDto.getTestStatus());	//db에 시험 참석 여부 저장
			
			String cheattime = "";
			for(String c : sttDto.getCheatTime()) {
				cheattime+=c+"/";
			}
			stt.setCheatTime(cheattime);	//컨닝 시간 저장(list to string)
			
			if(sttDto.getCheatTime().size()==0) { //컨닝 여부 저장
				stt.setIsCheating("false");
			}else {
				stt.setIsCheating("true");
			}
			
			String answer = "";
			for(String c : sttDto.getTestAnswer()) {
				cheattime+=c+"/";
			}
			stt.setTestAnswer(answer);	//작성 답안 목록 저장(list to string)
			
			if(studentTestRepository.save(stt)!=null) {
				return true;
			}else {
				return false;
			}
			
		} else {
			logger.info("{} 학생 시험 답안, 녹화파일 등록 실패");
			return false;
		}
	}

	@Transactional
	public boolean updateTestScore(StudentTestDto sttDto) {	//강사가 학생 점수 update
		
		Optional<StudentTest> sttOpt = studentTestRepository.findById(sttDto.getTestNum());
		
		if (sttOpt.isPresent()) {
			try {
				StudentTest stt = sttOpt.get();
				stt.setTestResult(sttDto.getTestResult());			
				
				if(studentTestRepository.save(stt)!=null) {
					logger.info("{} 학생 시험점수 update성공");
					return true;
				}else {
					logger.info("{} 학생 시험점수 update실패");
					return false;
				}
			}catch(Exception e) {
				e.printStackTrace();
				logger.info("{} 학생 시험점수 update실패");
				return false;
			}		
			
		} else {
			logger.info("{} 학생 시험점수 update실패");
			return false;
		}
	}
}
