package io.educare.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.TestProblemDto;
import io.educare.entity.Test;
import io.educare.entity.TestProblem;
import io.educare.repository.TestProblemRepository;
import io.educare.repository.TestRepository;

@Service
public class TestProblemServiceImpl implements TestProblemService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private ModelMapper mapper;

	private final TestProblemRepository tProblemRepository;
	private final TestRepository testRepository;

	public TestProblemServiceImpl(TestProblemRepository tProblemRepository, TestRepository testRepository,
			ModelMapper mapper) {
		this.tProblemRepository = tProblemRepository;
		this.testRepository = testRepository;
		this.mapper = mapper;
	}

	@Transactional
	public Boolean insertTProblem(Long testnum, TestProblem tProblem, MultipartFile mfile) {

		String imgname = null;
		try {
			try {
				imgname = String.valueOf(System.currentTimeMillis()) + mfile.getOriginalFilename();
				mfile.transferTo(
						new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\tproblemimg\\" + imgname));
				logger.info("{}번 문제 이미지 등록", tProblem.getProId());
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				logger.error("{}번 문제 이미지 등록 실패", tProblem.getProId());
			}
			Optional<Test> testOpt = testRepository.findById(testnum);

			if (testOpt.isPresent()) {
				tProblem.setProImage(imgname);
				tProblem.setTestNum(testOpt.get());

				TestProblem testProblem = tProblemRepository.save(tProblem);
				testOpt.get().getProblemList().add(testProblem);

				logger.info("{} 시험 문제 등록 성공", testnum);
				return true;
			} else {
				logger.info("{} 시험 등록 실패", testnum);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("{} 시험 등록 실패", testnum);
			return false;
		}
	}

	@Transactional
	public Boolean insertTProblemNoimg(Long testnum, TestProblem tProblem) {
		Optional<Test> testOpt = testRepository.findById(testnum);
		try {
			if (testOpt.isPresent()) {
				tProblem.setProImage("default.png");
				tProblem.setTestNum(testOpt.get());

				TestProblem testProblem = tProblemRepository.save(tProblem);
				testOpt.get().getProblemList().add(testProblem);

				logger.info("{} 시험 문제 등록 성공", testnum);
				return true;
			} else {
				logger.info("{} 시험 문제 등록 실패", testnum);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("{} 시험 문제 등록 실패", testnum);
			return false;
		}
	}

	public List<TestProblemDto> getTProblemsByTNum(Long testnum) {

		List<TestProblem> tProblemlist = tProblemRepository.findAllTProblemByTNum(testnum);
		List<TestProblemDto> tProblemDto = tProblemlist.stream().map(t -> new TestProblemDto(t.getProId(),
				t.getProNum(), t.getProDes(), t.getProSel(), t.getProImage(), t.getProAnswer()))
				.collect(Collectors.toList());
		logger.info("{}번 시험 전체 문제 조회 요청", testnum);
		return tProblemDto;
	}

	public TestProblemDto getTProblem(Long pronum) {

		Optional<TestProblem> tProblemOpt = tProblemRepository.findById(pronum);
		TestProblemDto tProblemDto = mapper.map(tProblemOpt.get(), TestProblemDto.class);
		logger.info("{}번 문제 조회 요청", pronum);
		return tProblemDto;
	}

	@Transactional
	public Boolean updateTProblem(TestProblem tProblem, MultipartFile mfile) {

		Optional<TestProblem> findtProblem = tProblemRepository.findById(tProblem.getProNum());
		String imgname = null;
		try {
			if (findtProblem.isPresent()) {
				try {
					imgname = String.valueOf(System.currentTimeMillis()) + mfile.getOriginalFilename();
					mfile.transferTo(
							new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\tproblemimg\\" + imgname));
					logger.info("{} 시험 {}번 문제 이미지 수정", tProblem.getTestNum(), tProblem.getProNum());
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
					logger.error("{} 시험 {}번 문제 이미지 수정 실패", tProblem.getTestNum(), tProblem.getProNum());
				}
				TestProblem testProblem = findtProblem.get();

				testProblem.setProDes(tProblem.getProDes());
				testProblem.setProSel(tProblem.getProSel());
				testProblem.setProImage(imgname);
				testProblem.setProAnswer(tProblem.getProAnswer());

				tProblemRepository.save(testProblem);
				return true;
			} else {
				logger.error("{} 시험 {}번 문제 미존재 수정실패", tProblem.getTestNum(), tProblem.getProNum());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 시험 {}번 문제 수정실패", tProblem.getTestNum(), tProblem.getProNum());
			return false;
		}
	}

	@Transactional
	public Boolean updateTProblemNoimg(TestProblem tProblem) {

		Optional<TestProblem> findtProblem = tProblemRepository.findById(tProblem.getProNum());
		try {
			if (findtProblem.isPresent()) {
				TestProblem testProblem = findtProblem.get();

				testProblem.setProDes(tProblem.getProDes());
				testProblem.setProSel(tProblem.getProSel());
				testProblem.setProAnswer(tProblem.getProAnswer());

				tProblemRepository.save(tProblem);
				return true;
			} else {
				logger.error("{} 시험 {}번 문제 미존재 수정실패", tProblem.getTestNum(), tProblem.getProNum());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 시험 {}번 문제 수정실패", tProblem.getTestNum(), tProblem.getProNum());
			return false;
		}
	}

	@Transactional
	public Boolean deleteTProblem(Long pronum, Long testnum) {

		Optional<TestProblem> TProblemOpt = tProblemRepository.findById(pronum);
		Optional<Test> testOpt = testRepository.findById(testnum);
		
		try {
			if (TProblemOpt.isPresent() && testOpt.isPresent()) {

				String filename = TProblemOpt.get().getProImage();
				File file = new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\tproblemimg\\" + filename);

				if (file.exists() && !filename.equals("default.png")) {
					if (file.delete()) {
						logger.info("{} 시험문제 이미지 삭제 완료", pronum);
					} else {
						logger.error("{} 시험문제 이미지 삭제 실패", pronum);
					}
				}
				List<TestProblem> tProblemlist = testOpt.get().getProblemList();
				
				for (int i = 0; i < tProblemlist.size(); i++) {
					Integer idx = tProblemlist.indexOf(TProblemOpt.get());
					if (idx != -1) {
						tProblemlist.remove(idx);
					} else {
						logger.error("{} 시험 삭제 실패", testnum);
						return false;
					}
				}
				
				tProblemRepository.delete(TProblemOpt.get());
				logger.info("{} 시험문제 삭제 완료", pronum);
				return true;
			} else {
				logger.error("{} 시험문제 삭제 실패", pronum);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 시험문제 삭제 실패", pronum);
			return false;
		}
	}

}