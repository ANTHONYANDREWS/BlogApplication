package dao;

import model.User;

public interface UserDaoInterface {
	
	int signup(User user);
	boolean loginUser(User user);

}
