package io.educare.service;

import java.util.List;

import io.educare.dto.TestDto;

public interface TestService {

	public Long insertTest(String username, TestDto testDto);
	
	public List<TestDto> getTestsByUsername(String username);
	
	public TestDto getTest(Long testnum);
	
	public Boolean updateTest(TestDto testDto);
	
	public Boolean deleteTest(String username, Long testnum);
	
}
