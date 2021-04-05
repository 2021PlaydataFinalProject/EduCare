package educare.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="role")
public abstract class User {
	
//	@Id
//	@Column(name = "user_num")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private long userNum;
	
	@Id
	@Column(name = "user_id")
	private String username;
	
	@Column(name = "user_pw")
	private String password;
	
	@Column(name = "user_name")
	private String userRealName;

	@Column(name = "user_phonenum")
	private String phoneNumber;
	
	@Column(name = "user_image")
	private String userImage;

}
