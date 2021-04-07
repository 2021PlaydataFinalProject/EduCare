package io.educare.service;

import java.util.List;

import io.educare.entity.Test;

public interface TestService {

	public Test insertTest(String username, Test test);
	
	public List<Test> getTestsByUsername(String username);
	
	public Test getTest(Long testnum);
	
}
