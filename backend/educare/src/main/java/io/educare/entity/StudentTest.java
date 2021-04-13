package io.educare.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentTest{
	
	@Id
	@Column(name = "st_test_num")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long stTestNum;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "stu_id")
	private Student stuId;
	
	@Column(name = "test_result")
	private String testResult;

	@Column(name = "is_cheating")
	private String isCheating;
	
	@Column(name = "cheat_time")
	private String cheatTime;

	@Column(name = "video_name")
	private String videoName;

	@Column(name = "test_answer")
	private String testAnswer;
	
	@Column(name = "test_status")
	private String testStatus;
	
	//@JsonManagedReference
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "test_num")
	private Test testNum;
}
