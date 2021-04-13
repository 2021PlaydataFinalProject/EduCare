package io.educare.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.educare.dto.TestDto;
import io.educare.entity.Instructor;
import io.educare.entity.Test;
import io.educare.entity.User;
import io.educare.repository.TestRepository;
import io.educare.repository.UserRepository;

@Service
public class TestServiceimpl implements TestService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final TestRepository testRepository;
	private final UserRepository userRepository;

	public TestServiceimpl(TestRepository testRepository, UserRepository userRepository) {
		this.testRepository = testRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public Long insertTest(String username, TestDto testDto) {
		Optional<User> userOpt = userRepository.findById(username);

		try {
			if (userOpt.isPresent()) {
				Instructor ins = (Instructor) userOpt.get();
				Test newTest = new Test();
				newTest.setTestName(testDto.getTestName());
				newTest.setStartTime(testDto.getStartTime());
				newTest.setEndTime(testDto.getEndTime());
				newTest.setTestGuide(testDto.getTestGuide());
				newTest.setInsId(ins);

				Test savedtest = testRepository.save(newTest);
				ins.getTestList().add(savedtest);
				
				logger.info("{} 강사 시험 등록 성공", username);
				return savedtest.getTestNum();
			} else {
				logger.info("{} 강사 시험 등록 실패", username);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 강사 시험 등록 실패", username);
			return null;
		}
	}

	public List<TestDto> getTestsByUsername(String username) {
		List<Test> testlist = testRepository.findAllTestByUsername(username);
		List<TestDto> collect = testlist.stream().map(
				t -> new TestDto(t.getTestNum(), t.getTestName(), t.getStartTime(), t.getEndTime(), t.getTestGuide()))
				.collect(Collectors.toList());
		
		logger.info("{} 강사 출제 시험 전체 조회 요청", username);
		return collect;
	}

	public TestDto getTest(Long testnum) {
		Optional<Test> testOpt = testRepository.findById(testnum);
		
		if(testOpt.isPresent()) {
			Test test = testOpt.get();
			TestDto testDto = TestDto.builder().testNum(test.getTestNum()).testName(test.getTestName())
					.startTime(test.getStartTime()).endTime(test.getEndTime()).testGuide(test.getTestGuide()).build();

			logger.info("{} 시험 조회 성공", testnum);
			return testDto;			
		} else {
			logger.info("{} 시험 조회 실패", testnum);
			return null;
		}
	}

	@Transactional
	public Boolean updateTest(TestDto testDto) {
		Optional<Test> testOpt = testRepository.findById(testDto.getTestNum());

		try {
			if (testOpt.isPresent()) {
				Test savedTest = testOpt.get();
				savedTest.setStartTime(testDto.getStartTime());
				savedTest.setEndTime(testDto.getEndTime());
				savedTest.setTestGuide(testDto.getTestGuide());
				savedTest.setTestName(testDto.getTestName());

				testRepository.save(savedTest);
				logger.info("{} 시험 수정 성공", testDto.getTestNum());
				return true;
			} else {
				logger.info("{} 강사 수정 실패", testDto.getTestNum());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 강사 수정 실패", testDto.getTestNum());
			return false;
		}
	}

	@Transactional
	public Boolean deleteTest(String username, Long testnum) {
		Optional<User> userOpt = userRepository.findById(username);
		Optional<Test> findTest = testRepository.findById(testnum);

		try {
			if (findTest.isPresent() && userOpt.isPresent()) {
				Instructor ins = (Instructor) userOpt.get();
				List<Test> testlist = ins.getTestList();

				for (int idx = 0; idx < testlist.size(); idx++) {
					if (testlist.get(idx).getTestNum() == testnum) {
						testlist.remove(idx);
						break;
					}
				}
				testRepository.delete(findTest.get());
				logger.info("{} 시험 삭제 완료", testnum);
				return true;
			} else {
				logger.info("{} 시험 삭제 실패", testnum);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 시험 삭제 실패", testnum);
			return false;
		}
	}

}