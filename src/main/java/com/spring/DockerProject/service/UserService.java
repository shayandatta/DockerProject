package com.spring.DockerProject.service;

import java.util.List;

import com.spring.DockerProject.models.User;

public interface UserService {

	public List<User> GetAllUsers();
	public List<User> AddUser(User user);
	public String GetSerFileSize();
}
