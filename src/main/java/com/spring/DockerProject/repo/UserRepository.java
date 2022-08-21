package com.spring.DockerProject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.DockerProject.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
}
