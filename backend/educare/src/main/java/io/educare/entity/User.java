package io.educare.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="role")
public abstract class User {
	
	@Id
	@Column(name = "user_id", length = 50)
	private String username;
	
	@JsonIgnore
	@Column(name = "user_pw", length = 200)
	private String password;
	
	@Column(name = "user_name", length = 50)
	private String userRealName;

	@Column(name = "user_phonenum", length = 50)
	private String phoneNumber;
	
	@Column(name = "user_image")
	private String userImage;
	
	@Column(name = "role", nullable = false, insertable = false, updatable = false)
	private String role;
}
