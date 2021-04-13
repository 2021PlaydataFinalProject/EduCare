package io.educare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.educare.entity.StudentTest;

public interface StudentTestRepository extends JpaRepository<StudentTest, Long>{
	
	@Query(value = "SELECT * FROM studenttest WHERE stu_id=? and test_num=?" , nativeQuery = true)
	Optional<StudentTest> findStudentTestByUserNameAndTestNum(String username, long testnum);

	@Query(value = "SELECT * FROM studenttest WHERE stu_id=?" , nativeQuery = true)
	Optional <List<StudentTest>> findAllStudentTestByUserName(String username);
	
	@Query(value = "SELECT * FROM studenttest WHERE test_num=?" , nativeQuery = true)
	Optional <List<StudentTest>> findAllStudentTestByTestNum(long testnum);
	
}
