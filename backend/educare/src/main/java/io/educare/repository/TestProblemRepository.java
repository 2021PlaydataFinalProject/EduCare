package io.educare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.educare.entity.TestProblem;

public interface TestProblemRepository extends JpaRepository<TestProblem, Long> {
	
	@Query(value = "SELECT * FROM testproblem WHERE test_num=? ORDER BY pro_num", nativeQuery = true)
	List<TestProblem> findAllTProblemByTNum(Long testnum);
	
}
