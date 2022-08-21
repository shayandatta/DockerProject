package com.spring.DockerProject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.DockerProject.models.User;
import com.spring.DockerProject.service.UserService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/user")
public class UserRest {

	@Autowired
	@Qualifier("UserServiceDBImpl")
	UserService userService;
	
	@ApiOperation(value = "Sample test", notes = "Indicates the service is up and running")
	@GetMapping("/")
	public String Home()
	{
		return "Docker Project is running";
	}
	
	@ApiOperation(value = "Fetches all users", notes = "Fetches all saved users")
	@GetMapping("/user")
	public List<User> GetUsers()
	{
		return userService.GetAllUsers();
	}
	
	@ApiOperation(value = "Adds user", notes = "Adds a new user")
	@PostMapping("/user")
	public List<User> AddUser(@RequestBody User user)
	{
		return userService.AddUser(user);
	}
	
	@ApiOperation(value = "Displays file size", notes = "Displays the size of data, the service has used up.")
	@GetMapping("/userfile")
	public String GetFileSize()
	{
		return userService.GetSerFileSize();
	}
}
