package com.spring.DockerProject.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import com.spring.DockerProject.models.User;
import com.spring.DockerProject.repo.UserRepository;

@Service
@Qualifier("UserServiceDBImpl")
@EnableCaching
public class UserServiceDBImpl implements UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceDBImpl.class);

	@Autowired
	UserRepository userrepo;

	@Cacheable(value = "UserCache" ,key="'users'")
	@Override
	public List<User> GetAllUsers() {
		logger.info("GetAllUsers invoked");
		return userrepo.findAll();
	}

	@CachePut(value = "UserCache",key="'users'")
	@Override
	public List<User> AddUser(User user) {
		logger.info("AddUser invoked");
		userrepo.save(user);
		return userrepo.findAll();
	}

	@Override
	public String GetSerFileSize() {
		logger.info("GetSerFileSize invoked");
		return "DB size is yet to be implemented";
	}

}
