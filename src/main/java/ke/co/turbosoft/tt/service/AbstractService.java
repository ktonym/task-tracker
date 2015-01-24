package ke.co.turbosoft.tt.service;

import ke.co.turbosoft.tt.entity.User;
import ke.co.turbosoft.tt.repo.UserRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {
	
	final protected Logger logger =	
			LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected UserRepo userRepo;
	
	protected final String USER_INVALID = "Not a valid user";
	protected final String USER_NOT_ADMIN = "Not an admin user";
	
	protected boolean isValidUser(String username){
		User user = userRepo.findOne(username);
		return user != null;
	}

}
