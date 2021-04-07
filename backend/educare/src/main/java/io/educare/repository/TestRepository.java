package io.educare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.educare.entity.Test;

public interface TestRepository extends JpaRepository<Test, Long>{
	
	@Query(value = "SELECT * FROM test WHERE ins_id=? ORDER BY test_num", nativeQuery = true)
	List<Test> findAllTestByUsername(String username);
}
