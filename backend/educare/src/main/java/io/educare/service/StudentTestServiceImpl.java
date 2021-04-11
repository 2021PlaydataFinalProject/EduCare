package io.educare.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.StudentTestDto;
import io.educare.entity.Student;
import io.educare.entity.StudentTest;
import io.educare.entity.Test;
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
				
				if(studentTestRepository.save(studentTest)==null) {
					logger.info("{} 학생 시험 매칭 등록 실패");
					return false;
				}else {
					logger.info("{} 학생 시험 매칭 등록 성공", studentTest.getStTestNum());
					return true;
				}
			} else {
				logger.info("{} 학생 시험 매칭 등록 실패");
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public StudentTest getStudentTest(String username, long testNum) {	//id, testnum으로 studenttest 객체 정보 반환
		
		try {
			Optional<StudentTest> stTestOpt = studentTestRepository.findStudentTestByUserNameAndTestNum(username,testNum);
			StudentTest stTest = stTestOpt.get();
			
			if(stTest==null) {
				return null;
			}else {
				return stTest;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}		
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
