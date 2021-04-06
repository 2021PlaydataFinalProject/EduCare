package io.educare.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("ROLE_STUDENT")
public class Student  extends User {
	@Builder
	public Student() {
		super();
	}
	@Builder
	public Student(String username, String password, String userRealName, String phoneNumber,
			String userImage, List<StudentTest> testList) {
		super(username, password, userRealName, phoneNumber, userImage, "ROLE_STUDENT");
		this.testList = testList;
	}
	@OneToMany(mappedBy="stuId")
	List<StudentTest> testList = new ArrayList<StudentTest>();
}
