package io.educare.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.TestProblemDto;
import io.educare.entity.TestProblem;

public interface TestProblemService {

	public Boolean insertTProblem(Long testnum, TestProblem tProblem, MultipartFile mfile);
	
	public Boolean insertTProblemNoimg(Long testnum, TestProblem tProblem);

	public List<TestProblemDto> getTProblemsByTNum(Long testnum);
	
	public TestProblemDto getTProblem(Long pronum);
	
	public Boolean updateTProblem(TestProblem tProblem, MultipartFile mfile);
	
	public Boolean updateTProblemNoimg(TestProblem tProblem);
	
	public Boolean deleteTProblem(Long pronum, Long testnum);
	
}
