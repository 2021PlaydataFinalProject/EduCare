package io.educare.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.TestProblemDto;

public interface TestProblemService {

	public Boolean insertTProblem(long testnum, TestProblemDto tProblemDto, MultipartFile mfile);
	
	public Boolean insertTProblemNoimg(long testnum, TestProblemDto tProblemDto);

	public List<TestProblemDto> getTProblemsByTNum(long testnum);
	
	public TestProblemDto getTProblem(long proid);
	
	public Boolean updateTProblem(TestProblemDto tProblemDto, MultipartFile mfile);
	
	public Boolean updateTProblemNoimg(TestProblemDto tProblemDto);
	
	public Boolean deleteTProblem(long proid, long testnum);
	
}
