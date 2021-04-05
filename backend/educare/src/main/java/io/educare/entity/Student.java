package educare.entity;

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
public class Student  extends User{
	@Builder
	public Student() {
		super();
	}
	@Builder
	public Student(String username, String password, String studentName, String phoneNumber,
			String studentImage, List<StudentTest> testList) {
		super(username, password, studentName, phoneNumber, studentImage);
		this.testList = testList;
	}
	@OneToMany(mappedBy="stuId")
	List<StudentTest> testList = new ArrayList<StudentTest>();
}
