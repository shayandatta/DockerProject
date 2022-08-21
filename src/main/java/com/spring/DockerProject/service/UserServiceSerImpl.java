package com.spring.DockerProject.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring.DockerProject.models.User;

@Service
@Qualifier("UserServiceSerImpl")
public class UserServiceSerImpl implements UserService{
	
	@Value("${File.path}")
	private String filePath;

	private ObjectOutputStream oos;
	private ObjectInputStream in;
	
	private FileOutputStream fos;
	private FileInputStream fis;
	
	private void initStream(boolean requireOutputStream) throws FileNotFoundException, IOException {
		
		File fs = new File(filePath);
		if (requireOutputStream) {
			fos = new FileOutputStream(fs.getAbsolutePath());
			oos = new ObjectOutputStream(fos);
		} else {
			if (fs.exists()) {
				fis = new FileInputStream(fs.getAbsolutePath());
				in = new ObjectInputStream(fis);
			}
		}
	}

	@Override
	public List<User> GetAllUsers() {
		List<User> users = new ArrayList<User>();
		try {
			initStream(false);
			if (in != null && in.readObject() instanceof List)
				users = (List<User>) in.readObject();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			closeStrem();
		}
		return users;
	}

	@Override
	public List<User> AddUser(User user) {
		List<User> users = null;
		try {			
			users = GetAllUsers();			
			users.add(user);
			initStream(true);
			oos.writeObject(users);
		} 
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		finally
		{
			closeStrem();
		}
		return users;
	}
	
	private void closeStrem() {
		try {
			if (fis != null) {
				fis.close();
				in.close();
			}

			if (fos != null) {
				fos.close();
				oos.close();
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	
	@Override
	public String GetSerFileSize() {
		
		File fs = new File(filePath);
		String txt = "%s :: file size : %d bytes";
		if(fs.exists())
		{
			txt = String.format(txt, fs.getAbsolutePath(),fs.length());
			System.err.println(txt);
		}
		return txt;
	}  

}
