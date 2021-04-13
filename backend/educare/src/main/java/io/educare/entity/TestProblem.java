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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TestProblem {
	
	@Id
	@Column(name = "pro_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long proId;
	
	@Column(name = "pro_num")
	private int proNum;
	
	@Column(name = "pro_des")
	private String proDes;
	
	@Column(name = "pro_sel")
	private String proSel;
	
	@Column(name = "pro_image")
	private String proImage;
	
	@Column(name = "pro_answer")
	private String proAnswer;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "test_num")
	private Test testNum;
}
