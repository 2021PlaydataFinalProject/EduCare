package io.educare.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentTestDto {
	
	private String username;
	
	private String userRealName;
	
	private String testName;
	
	private String testResult;
	
	private String isCheating;
	
	private List<String> cheatTime;

	private String videoName;

	private List<String> testAnswer;
	
	private String testStatus;
	
	private long testNum;
}
