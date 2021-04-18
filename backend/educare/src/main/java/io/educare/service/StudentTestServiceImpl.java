package io.educare.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public StudentTestServiceImpl(TestRepository testRepository, StudentRepository studentRepository,
			StudentTestRepository studentTestRepository) {
		this.testRepository = testRepository;
		this.studentRepository = studentRepository;
		this.studentTestRepository = studentTestRepository;
	}

	@Transactional
	public boolean insertStudentTest(String username, long testNum) { // 강사가 시험에 학생을 추가하면 studenttest table에 학생id, 시험id와
																		// 함께 insert
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
				studentTest.setTestStatus("T");

				if (studentTestRepository.save(studentTest) == null) {
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
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 학생 시험 매칭 등록 실패");
			return false;
		}

	}

	public StudentTestDto getStudentTest(String username, long testNum) {
		Optional<StudentTest> stTestOpt = studentTestRepository.findStudentTestByUserNameAndTestNum(username, testNum);

		try {
			if (stTestOpt.isPresent()) {
				StudentTest stTest = stTestOpt.get();
				System.out.println(stTest.getStuId());
				

				List<String> cheatTimes = Arrays.asList(stTest.getCheatTime().split("/"));
				List<String> testAnswers = Arrays.asList(stTest.getTestAnswer().split("/"));

				StudentTestDto stuTestDto = StudentTestDto.builder()
						.userRealName(studentRepository.findById(username).get().getUserRealName()).username(username)
						.testName(testRepository.findById(testNum).get().getTestName()).testNum(testNum)
						.isCheating(stTest.getIsCheating()).cheatTime(cheatTimes).videoName(stTest.getVideoName())
						.testAnswer(testAnswers).testStatus(stTest.getTestStatus()).testResult(stTest.getTestResult())
						.build();

				logger.info("{} 학생 {} 시험 정보 조회 성공", username, testNum);
				return stuTestDto;
			} else {
				logger.info("{} 학생 {} 시험 정보 조회 실패", username, testNum);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 학생 {} 시험 정보 조회 실패", username, testNum);
			return null;
		}
	}

	public List<StudentTestDto> getStudentTestListByUname(String username) {
		Optional<List<StudentTest>> stuTestList = studentTestRepository.findAllStudentTestByUserName(username);

		Stream<StudentTest> rawStuTestListBef = stuTestList.get().stream().filter(st -> st.getTestAnswer() == null);
		Stream<StudentTest> rawStuTestListAft = stuTestList.get().stream().filter(st -> st.getTestAnswer() != null);

		List<StudentTestDto> stuTestDtoListBef = rawStuTestListBef.map(st -> new StudentTestDto(username, null,
				testRepository.findById(st.getTestNum().getTestNum()).get().getTestName(), null, null, null, null, null,
				st.getTestStatus(), st.getTestNum().getTestNum())).collect(Collectors.toList());
		List<StudentTestDto> stuTestDtoListAft = rawStuTestListAft
				.map(st -> new StudentTestDto(username, null,
						testRepository.findById(st.getTestNum().getTestNum()).get().getTestName(), st.getTestResult(),
						st.getIsCheating(), Arrays.asList(st.getCheatTime().split("/")), st.getVideoName(),
						Arrays.asList(st.getTestAnswer().split("/")), st.getTestStatus(), st.getTestNum().getTestNum()))
				.collect(Collectors.toList());

		List<StudentTestDto> joined = new ArrayList<StudentTestDto>();
		joined.addAll(stuTestDtoListBef);
		joined.addAll(stuTestDtoListAft);

		logger.info("{} 학생 모든 시험 정보 조회", username);
		return joined;
	}

	public List<StudentTestDto> getStudentTestListByTNum(long testnum) {
		Optional<List<StudentTest>> stuTestList = studentTestRepository.findAllStudentTestByTestNum(testnum);

		List<StudentTestDto> stuTestDtoList = stuTestList.get().stream()
				.map(st -> new StudentTestDto(st.getStuId().getUsername(),
						studentRepository.findById(st.getStuId().getUsername()).get().getUserRealName(), null,
						st.getTestResult(), st.getIsCheating(), null, null, null, st.getTestStatus(),
						st.getTestNum().getTestNum()))
				.collect(Collectors.toList());
		logger.info("{} 시험 응시 학생 정보 조회", testnum);
		return stuTestDtoList;
	}

	@Transactional
	   public boolean updateMyTest(StudentTestDto sttDto, MultipartFile mfile) { // studenttest table update
	      System.out.println("!!!!!!!!!!!!!!!!!!!!!!"+sttDto.getUsername()+"   "+sttDto.getTestNum());
	      System.out.println("!!!!!!!!!!!!!!!!!!!!!!"+mfile);
	                                                         
	      Optional<StudentTest> sttOpt = studentTestRepository.findStudentTestByUserNameAndTestNum(sttDto.getUsername(), sttDto.getTestNum());
	      
	      if (sttOpt.isPresent()) {
	         StudentTest stt = sttOpt.get();
	         String videoname;

	         try {
	            videoname = sttDto.getTestNum() +"_" + sttDto.getUsername() +"_" + "video.mp4";
	            mfile.transferTo(
	                  new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\tproblemvideo\\" + videoname));
	            logger.info("{}번 문제 녹화파일 등록 성공", sttDto.getTestNum());
	            stt.setVideoName(videoname); // db에 video파일명 저장
	         } catch (IllegalStateException | IOException e) {
	            e.printStackTrace();
	            logger.error("{}번 문제 녹화파일 등록 실패", sttDto.getTestNum());
	            return false;
	         }
	         stt.setTestStatus(sttDto.getTestStatus()); // db에 시험 참석 여부 저장

	         String answer = "";
	         for (String c : sttDto.getTestAnswer()) {
	            answer += c + "/";
	         }
	         stt.setTestAnswer(answer); // 작성 답안 목록 저장(list to string)

	         if (studentTestRepository.save(stt) != null) {
	            logger.info("{} 학생 시험 답안, 녹화파일 등록 성공", sttDto.getUsername());
	            return true;
	         } else {
	            logger.info("{} 학생 시험 답안, 녹화파일 등록 실패", sttDto.getUsername());
	            return false;
	         }
	      } else {
	         logger.info("{} 학생 시험 답안, 녹화파일 등록 실패", sttDto.getUsername());
	         return false;
	      }
	   }

	@Transactional
	public boolean updateTestScore(String username, long testnum, String testresult) { // 강사가 학생 점수 update
		Optional<StudentTest> sttOpt = studentTestRepository.findStudentTestByUserNameAndTestNum(username, testnum);

		if (sttOpt.isPresent()) {
			try {
				StudentTest stt = sttOpt.get();
				stt.setTestResult(testresult);

				if (studentTestRepository.save(stt) != null) {
					logger.info("{} 학생 {} 시험점수 update성공", username, testnum);
					return true;
				} else {
					logger.info("{} 학생 {} 시험점수 update실패", username, testnum);
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("{} 학생 {} 시험점수 update실패", username, testnum);
				return false;
			}
		} else {
			logger.info("{} 학생 {} 시험점수 update실패", username, testnum);
			return false;
		}
	}

	@Transactional
	public boolean deleteStudentTestByUserName(String username, long testnum) {
		Optional<StudentTest> sttOpt = studentTestRepository.findStudentTestByUserNameAndTestNum(username, testnum);
		
		try {
			if (sttOpt.isPresent()) {
				studentTestRepository.delete(sttOpt.get());
				logger.info("{} 학생 {} 시험정보 삭제 성공", username, testnum);
				return true;
			} else {
				logger.info("{} 학생 {} 시험정보 미존재 삭제 실패", username, testnum);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 학생 {} 시험정보 삭제 실패", username, testnum);
			return false;
		}
	}
}