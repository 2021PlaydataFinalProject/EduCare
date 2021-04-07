package io.educare.dto;

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
public class TestProblemDto {
		
	   @NotNull
	   @Size(min = 8, max = 50)
	   private long proId;
	
	   @NotNull
	   @Size(min = 8, max = 20)
	   private long proNum;
	   
	   @NotNull
	   @Size(min = 8)
	   private String proDes;

	   @NotNull
	   @Size(min = 2)
	   private String proSel;
	   
	   @NotNull
	   @Size(min = 2, max = 100)
	   private String proImage;
	   
	   @Size(min = 2, max = 50)
	   private String proAnswer;

}
