package com.revature.cardealership.dao;

import com.revature.cardealership.model.User;

public interface UserDAO {

	boolean addUser(User user);
	
	User getUserByUsername(String username);

	User getByUsernameAndPassword(String username, String password);

}
