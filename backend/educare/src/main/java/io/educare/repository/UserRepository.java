package io.educare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.educare.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	@Query(value = "SELECT * FROM user WHERE role=?", nativeQuery = true)
	List<User> findAllUserByRole(String student);
	
	@Query(value = "select * from user where user_id not in (select stu_id from studenttest where test_num=?) and role=?", nativeQuery = true)
	List<User> findAllUserNotInTest(long testnum, String role);
	
	}
