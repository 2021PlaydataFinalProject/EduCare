package io.educare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.educare.entity.Student;


public interface StudentRepository extends JpaRepository<Student, String> {
	
}
