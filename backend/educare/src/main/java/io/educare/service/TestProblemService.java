package io.educare.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.educare.entity.TestProblem;

public interface TestProblemService {

	public TestProblem insertTProblem(Long testnum, TestProblem tProblem, MultipartFile mfile);
	
	public TestProblem insertTProblemNoimg(Long testnum, TestProblem tProblem);

	public List<TestProblem> getTProblemsByTNum(Long testnum);
	
	public TestProblem getTProblem(Long pronum);
	
	public TestProblem updateTProblem(TestProblem tProblem, MultipartFile mfile);
	
	public TestProblem updateTProblemNoimg(TestProblem tProblem);
	
	public void deleteTProblem(Long pronum);
	
}
