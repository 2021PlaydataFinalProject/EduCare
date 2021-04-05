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
@DiscriminatorValue("ROLE_ADMIN")
public class Admin  extends User{
	@Builder
	public Admin() {
		super();
	}
	@Builder
	public Admin(String username, String password, String studentName, String phoneNumber,
			String studentImage, List<StudentTest> testList) {
		super(username, password, studentName, phoneNumber, studentImage);
	}
}
