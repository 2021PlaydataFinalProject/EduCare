package io.educare.entity;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@DiscriminatorValue("ROLE_ADMIN")
public class Admin  extends User {
	@Builder
	public Admin() {
		super();
	}
	@Builder
	public Admin(String username, String password, String studentName, String phoneNumber,
			String studentImage, List<StudentTest> testList) {
		super(username, password, studentName, phoneNumber, studentImage, "ROLE_ADMIN");
	}
}
