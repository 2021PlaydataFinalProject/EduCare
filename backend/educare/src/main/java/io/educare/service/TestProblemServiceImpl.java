package io.educare.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.TestProblemDto;
import io.educare.entity.Test;
import io.educare.entity.TestProblem;
import io.educare.entity.User;
import io.educare.jwt.TokenProvider;
import io.educare.repository.TestProblemRepository;
import io.educare.repository.TestRepository;
import io.educare.util.CookieUtil;

@Service
public class TestProblemServiceImpl implements TestProblemService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final TestProblemRepository tProblemRepository;
	private final TestRepository testRepository;

	public TestProblemServiceImpl(TestProblemRepository tProblemRepository, TestRepository testRepository) {
		this.tProblemRepository = tProblemRepository;
		this.testRepository = testRepository;
	}

	@Transactional
	public TestProblem insertTProblem(Long testnum, TestProblem tProblem, MultipartFile mfile) {

		String imgname = null;

		try {
			imgname = String.valueOf(System.currentTimeMillis()) + mfile.getOriginalFilename();
			mfile.transferTo(new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\tproblemimg\\" + imgname));
			logger.info("{}번 문제 이미지 등록", tProblem.getProId());
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			logger.error("{}번 문제 이미지 등록", tProblem.getProId());
		}
		Optional<Test> testOpt = testRepository.findById(testnum);

		if (testOpt.isPresent()) {
			tProblem.setProImage(imgname);
			tProblem.setTestNum(testOpt.get());
			TestProblem testProblem = tProblemRepository.save(tProblem);
			logger.info("{} 시험 문제 등록 성공", testnum);
			return testProblem;
		} else {
			logger.info("{} 시험 등록 실패", testnum);
			return null;
		}

	}

	@Transactional
	public TestProblem insertTProblemNoimg(Long testnum, TestProblem tProblem) {
		Optional<Test> testOpt = testRepository.findById(testnum);
		
		if (testOpt.isPresent()) {
			tProblem.setProImage("default.png");
			tProblem.setTestNum(testOpt.get());
			TestProblem testProblem = tProblemRepository.save(tProblem);
			logger.info("{} 시험 문제 등록 성공", testnum);
			return testProblem;
		} else {
			logger.info("{} 시험 문제 등록 실패", testnum);
			return null;
		}
	}

	public List<TestProblem> getTProblemsByTNum(Long testnum) {

		logger.info("{}번 시험 전체 문제 조회 요청", testnum);
		return tProblemRepository.findAllTProblemByTNum(testnum);
	}

	public TestProblem getTProblem(Long pronum) {

		logger.info("{}번 문제 조회 요청", pronum);
		return tProblemRepository.findById(pronum).get();
	}

	@Transactional
	public TestProblem updateTProblem(TestProblem tProblem, MultipartFile mfile) {

		Optional<TestProblem> findtProblem = tProblemRepository.findById(tProblem.getProNum());
		String imgname = null;

		if (findtProblem.isPresent()) {
			try {
				imgname = String.valueOf(System.currentTimeMillis()) + mfile.getOriginalFilename();
				mfile.transferTo(
						new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\tproblemimg\\" + imgname));
				logger.info("{} 시험 {}번 문제 이미지 수정", tProblem.getTestNum(), tProblem.getProNum());
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				logger.error("{} 시험 {}번 문제 이미지 수정", tProblem.getTestNum(), tProblem.getProNum());
			}
			TestProblem testProblem = findtProblem.get();
			testProblem.setProDes(tProblem.getProDes());
			testProblem.setProSel(tProblem.getProSel());
			testProblem.setProImage(imgname);
			testProblem.setProAnswer(tProblem.getProAnswer());

			return tProblemRepository.save(testProblem);
		} else {
			logger.error("{} 시험 {}번 문제 미존재 수정실패", tProblem.getTestNum(), tProblem.getProNum());
			return null;
		}
	}

	@Transactional
	public TestProblem updateTProblemNoimg(TestProblem tProblem) {

		Optional<TestProblem> findtProblem = tProblemRepository.findById(tProblem.getProNum());

		if (findtProblem.isPresent()) {
			TestProblem testProblem = findtProblem.get();

			testProblem.setProDes(tProblem.getProDes());
			testProblem.setProSel(tProblem.getProSel());
			testProblem.setProAnswer(tProblem.getProAnswer());

			return tProblemRepository.save(tProblem);
		} else {
			logger.error("{} 시험 {}번 문제 미존재 수정실패", tProblem.getTestNum(), tProblem.getProNum());
			return null;
		}
	}

	@Transactional
	public void deleteTProblem(Long pronum) {

		Optional<TestProblem> findTProblem = tProblemRepository.findById(pronum);

		if (findTProblem.isPresent()) {

			String filename = findTProblem.get().getProImage();
			File file = new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\tproblemimg\\" + filename);

			if (file.exists() && !filename.equals("default.png")) {
				if (file.delete()) {
					logger.info("{} 시험문제 이미지 삭제 완료", pronum);
				} else {
					logger.error("{} 시험문제 이미지 삭제 실패", pronum);
				}
			}
			tProblemRepository.delete(findTProblem.get());
			logger.info("{} 시험문제 삭제 완료", pronum);
		} else {
			logger.error("{} 시험문제 삭제 실패", pronum);
		}
	}

}
