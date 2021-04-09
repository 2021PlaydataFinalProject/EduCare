package io.educare.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
	
	   @NotNull
	   @Size(min = 8, max = 50)
	   private long testNum;
	   
	   @NotNull
	   @Size(min = 8, max = 50)
	   private String testName;

	   @NotNull
	   @Size(min = 2, max = 50)
	   private LocalDateTime startTime;
	   
	   @NotNull
	   @Size(min = 2, max = 50)
	   private LocalDateTime endTime;
	   
	   @Size(min = 2, max = 255)
	   private String testGuide;
	
}
