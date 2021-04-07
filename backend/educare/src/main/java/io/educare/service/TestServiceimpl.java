package io.educare.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.educare.entity.Instructor;
import io.educare.entity.Test;
import io.educare.entity.TestProblem;
import io.educare.entity.User;
import io.educare.repository.TestProblemRepository;
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
	public Test insertTest(String username, Test test) {
		Optional<User> userOpt = userRepository.findById(username);
		if (userOpt.isPresent()) {
			Instructor ins = (Instructor) userOpt.get();
			test.setInsId(ins);
			Test savedtest = testRepository.save(test);
			logger.info("{} 강사 시험 등록 성공", username);
			return savedtest;
		} else {
			logger.info("{} 강사 시험 등록 실패", username);
			return null;
		}
	}
	
	public List<Test> getTestsByUsername(String username) {

		logger.info("{} 강사 출제 시험 전체 조회 요청", username);
		return testRepository.findAllTestByUsername(username);
	}

	public Test getTest(Long testnum) {

		logger.info("{} 시험 조회 요청", testnum);
		return testRepository.findById(testnum).get();
	}
	
//	@Transactional
//	public Test updateTest(Test test) {
//		Optional<Test> testOpt = testRepository.findById(test.getTestNum());
//		if (testOpt.isPresent()) {
//			Test savedtest = testOpt.get().builder().
//			Test savedtest = testRepository.save(test);
//			logger.info("{} 강사 시험 등록 성공", username);
//			return savedtest;
//		} else {
//			logger.info("{} 강사 시험 등록 실패", username);
//			return null;
//		}
//	}
}
