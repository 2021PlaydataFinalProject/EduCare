package educare.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("ROLE_INSTRUCTOR")
public class Instructor  extends User{
	@Builder
	public Instructor() {
		super();
	}
	@Builder
	public Instructor(String username, String password, String studentName, String phoneNumber,
			String studentImage, List<Test> testList) {
		super(username, password, studentName, phoneNumber, studentImage);
		this.testList = testList;
	}
	@OneToMany(mappedBy="insId")
	List<Test> testList = new ArrayList<Test>();
}
