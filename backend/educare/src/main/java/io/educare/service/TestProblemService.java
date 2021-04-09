package io.educare.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.TestProblemDto;

public interface TestProblemService {

	public Boolean insertTProblem(Long testnum, TestProblemDto tProblemDto, MultipartFile mfile);
	
	public Boolean insertTProblemNoimg(Long testnum, TestProblemDto tProblemDto);

	public List<TestProblemDto> getTProblemsByTNum(Long testnum);
	
	public TestProblemDto getTProblem(Long pronum);
	
	public Boolean updateTProblem(TestProblemDto tProblemDto, MultipartFile mfile);
	
	public Boolean updateTProblemNoimg(TestProblemDto tProblemDto);
	
	public Boolean deleteTProblem(Long pronum, Long testnum);
	
}
