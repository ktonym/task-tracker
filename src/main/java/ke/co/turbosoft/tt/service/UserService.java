package ke.co.turbosoft.tt.service;

import java.util.List;

import ke.co.turbosoft.tt.entity.User;

public interface UserService {
	
	User findOne(String username);
	List<User> findAll();
	void delete(User user);
	User save(User user);
	

}
