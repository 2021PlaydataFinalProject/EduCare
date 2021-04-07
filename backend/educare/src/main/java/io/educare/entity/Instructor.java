package io.educare.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("ROLE_INSTRUCTOR")
public class Instructor  extends User {
	@Builder
	public Instructor() {
		super();
	}
	@Builder
	public Instructor(String username, String password, String userRealName, String phoneNumber,
			String userImage, List<Test> testList) {
		super(username, password, userRealName, phoneNumber, userImage, "ROLE_INSTRUCTOR");
		this.testList = testList;
	}
	@JsonManagedReference
	@OneToMany(mappedBy="insId", cascade = CascadeType.REMOVE)
	List<Test> testList = new ArrayList<Test>();
}
