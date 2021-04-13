package io.educare.service;

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

import io.educare.dto.TestProblemDto;
import io.educare.entity.Test;
import io.educare.entity.TestProblem;
import io.educare.repository.TestProblemRepository;
import io.educare.repository.TestRepository;

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
	public Boolean insertTProblem(long testnum, TestProblemDto tProblemDto, MultipartFile mfile) {
		String imgname = null;
		
		try {
			try {
				imgname = String.valueOf(System.currentTimeMillis()) + mfile.getOriginalFilename();
				mfile.transferTo(
						new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\tproblemimg\\" + imgname));
				logger.info("{}번 문제 이미지 등록", tProblemDto.getProId());
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				logger.error("{}번 문제 이미지 등록 실패", tProblemDto.getProId());
			}
			Optional<Test> testOpt = testRepository.findById(testnum);
			
			if (testOpt.isPresent()) {
				
				String proSel = String.join("/", tProblemDto.getProSel());
				TestProblem newtProblem = new TestProblem();
				newtProblem.setProNum(tProblemDto.getProNum());
				newtProblem.setProDes(tProblemDto.getProDes());
				newtProblem.setProSel(proSel);
				newtProblem.setProImage(imgname);
				newtProblem.setProAnswer(tProblemDto.getProAnswer());
				newtProblem.setTestNum(testOpt.get());
				
				TestProblem testProblem = tProblemRepository.save(newtProblem);
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
	public Boolean insertTProblemNoimg(long testnum, TestProblemDto tProblemDto) {
		Optional<Test> testOpt = testRepository.findById(testnum);

		try {
			if (testOpt.isPresent()) {
				String proSel = String.join("/", tProblemDto.getProSel());
				TestProblem newtProblem = new TestProblem();
				newtProblem.setProNum(tProblemDto.getProNum());
				newtProblem.setProDes(tProblemDto.getProDes());
				newtProblem.setProSel(proSel);
				newtProblem.setProImage("default.png");
				newtProblem.setProAnswer(tProblemDto.getProAnswer());
				newtProblem.setTestNum(testOpt.get());
				
				TestProblem testProblem = tProblemRepository.save(newtProblem);
				testOpt.get().getProblemList().add(testProblem);

				logger.info("{} 시험 문제 등록 성공", testnum);
				return true;
			} else {
				logger.info("{} 시험 문제 등록 실패", testnum);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 시험 문제 등록 실패", testnum);
			return false;
		}
	}

	public List<TestProblemDto> getTProblemsByTNum(long testnum) {
		List<TestProblem> tProblemlist = tProblemRepository.findAllTProblemByTNum(testnum);
		List<TestProblemDto> tProblemDto = tProblemlist.stream().map(t -> new TestProblemDto(t.getProId(),
				t.getProNum(), t.getProDes(), Arrays.asList(t.getProSel().split("/")) , t.getProImage(), t.getProAnswer()))
				.collect(Collectors.toList());
		
		logger.info("{}번 시험 전체 문제 조회 요청", testnum);
		return tProblemDto;
	}

	public TestProblemDto getTProblem(long proid) { 
		Optional<TestProblem> tProblemOpt = tProblemRepository.findById(proid);
		
		if(tProblemOpt.isPresent()) {
			TestProblem tProblem = tProblemOpt.get();
			List<String> proSelList = Arrays.asList(tProblem.getProSel().split("/"));
			TestProblemDto tProblemDto = TestProblemDto.builder().proId(tProblem.getProId()).proNum(tProblem.getProNum())
					.proDes(tProblem.getProDes()).proSel(proSelList).proImage(tProblem.getProImage()).proAnswer(tProblem.getProAnswer()).build();
						
			logger.info("{}번 문제 조회 성공", proid);
			return tProblemDto;
		} else {
			logger.info("{}번 문제 미존재 조회 실패", proid);
			return null;
		}
	}

	@Transactional
	public Boolean updateTProblem(TestProblemDto tProblemDto, MultipartFile mfile) {
		Optional<TestProblem> findtProblem = tProblemRepository.findById(tProblemDto.getProId());
		String imgname = null;
		
		try {
			if (findtProblem.isPresent()) {
				try {
					imgname = String.valueOf(System.currentTimeMillis()) + mfile.getOriginalFilename();
					mfile.transferTo(
							new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\tproblemimg\\" + imgname));
					
					String filename = findtProblem.get().getProImage();
					File file = new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\tproblemimg\\" + filename);
									
					if (file.exists() && !filename.equals("default.png")) {
						if (file.delete()) {
							logger.info("{}번 문제 기존 이미지 삭제 완료", tProblemDto.getProId());
						} else {
							logger.info("{}번 문제 기존 이미지 삭제 실패", tProblemDto.getProId());
						}
					}
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
					logger.error("{}번 문제 이미지 수정 실패", tProblemDto.getProId());
				}
				TestProblem testProblem = findtProblem.get();
				String proSel = String.join("/", tProblemDto.getProSel());
				
				testProblem.setProNum(tProblemDto.getProNum());
				testProblem.setProDes(tProblemDto.getProDes());
				testProblem.setProSel(proSel);
				testProblem.setProImage(imgname);
				testProblem.setProAnswer(tProblemDto.getProAnswer());

				tProblemRepository.save(testProblem);
				return true;
			} else {
				logger.info("{}번 문제 미존재 수정실패", tProblemDto.getProId());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}번 문제 수정실패", tProblemDto.getProId());
			return false;
		}
	}

	@Transactional
	public Boolean updateTProblemNoimg(TestProblemDto tProblemDto) {
		Optional<TestProblem> findtProblem = tProblemRepository.findById(tProblemDto.getProId());
	
		try {
			if (findtProblem.isPresent()) {
				TestProblem testProblem = findtProblem.get();
				String proSel = String.join("/", tProblemDto.getProSel());
				
				testProblem.setProNum(tProblemDto.getProNum());
				testProblem.setProDes(tProblemDto.getProDes());
				testProblem.setProSel(proSel);
				testProblem.setProAnswer(tProblemDto.getProAnswer());
				
				tProblemRepository.save(testProblem);
				return true;
			} else {
				logger.info("{}번 문제 미존재 수정실패", tProblemDto.getProId());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}번 문제 수정실패", tProblemDto.getProId());
			return false;
		}
	}

	@Transactional
	public Boolean deleteTProblem(long proid, long testnum) {
		Optional<TestProblem> TProblemOpt = tProblemRepository.findById(proid);
		Optional<Test> testOpt = testRepository.findById(testnum);
	
		try {
			if (TProblemOpt.isPresent() && testOpt.isPresent()) {
				String filename = TProblemOpt.get().getProImage();
				File file = new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\tproblemimg\\" + filename);

				if (file.exists() && !filename.equals("default.png")) {
					if (file.delete()) {
						logger.info("{} 시험문제 이미지 삭제 완료", proid);
					} else {
						logger.info("{} 시험문제 이미지 삭제 실패", proid);
					}
				}
				List<TestProblem> problemlist = testOpt.get().getProblemList();
				
				for(int idx = 0; idx < problemlist.size(); idx++) {
					if (problemlist.get(idx).getProId() == proid) {
						problemlist.remove(idx);
						break;
					}
				}
				tProblemRepository.delete(TProblemOpt.get());
				logger.info("{} 시험문제 삭제 완료", proid);
				return true;
			} else {
				logger.info("{} 시험문제 삭제 실패", proid);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 시험문제 삭제 실패", proid);
			return false;
		}
	}

}