package io.educare.service;

import java.util.List;

import io.educare.dto.TestDto;
import io.educare.entity.Test;

public interface TestService {

	public Boolean insertTest(String username, Test test);
	
	public List<TestDto> getTestsByUsername(String username);
	
	public TestDto getTest(Long testnum);
	
	public Boolean updateTest(Test test);
	
	public Boolean deleteTest(String username, Long testnum);
	
}
