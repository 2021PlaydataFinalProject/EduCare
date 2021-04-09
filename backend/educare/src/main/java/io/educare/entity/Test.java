package io.educare.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Test {
	
	@Id
	@Column(name = "test_num")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long testNum;
	
	@Column(name = "test_name")
	private String testName;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul") // 2018-12-15T10:00:00
	@Column(name = "start_time")
	private LocalDateTime startTime;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	@Column(name = "end_time")
	private LocalDateTime endTime;

	@Column(name = "test_guide")
	private String testGuide;
	
	@ManyToOne
	@JoinColumn(name = "ins_id")
	private Instructor insId;
	
	//@JsonBackReference
	@OneToMany(mappedBy="testNum", cascade = CascadeType.REMOVE)
	List<StudentTest> studentList = new ArrayList<StudentTest>();
	
	//@JsonBackReference
	@OneToMany(mappedBy="testNum", cascade = CascadeType.REMOVE)
	List<TestProblem> problemList = new ArrayList<TestProblem>();
}
