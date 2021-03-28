package io.educare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.educare.domain.UserImg;

public interface UserImgRepository extends JpaRepository<UserImg, Long> {

}
