package ke.co.turbosoft.tt.service;

import ke.co.turbosoft.tt.entity.User;
import ke.co.turbosoft.tt.repo.TaskLogRepo;
import ke.co.turbosoft.tt.vo.Result;
import ke.co.turbosoft.tt.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ktonym on 2/8/15.
 */
@Transactional
@Service("userService")
public class UserServiceImpl extends AbstractService implements UserService {

    @Autowired
    protected TaskLogRepo taskLogRepo;

    public UserServiceImpl() {
        super();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<User> store(
            String username,
            String firstName,
            String lastName,
            String email,
            String password,
            Character adminRole,
            String actionUsername) {

        User actionUser = userRepo.findOne(actionUsername);

        if(!actionUser.isAdmin()){
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }

        if(username==null || username.trim().isEmpty() || email == null || email.trim().isEmpty()){
            return ResultFactory.getFailResult("Unable to store a user without a valid non-empty username/email");
        }

        if(password==null || password.length()==0){
            return ResultFactory.getFailResult("Unable to store a user without a valid non-empty password");
        }

        if(!adminRole.equals('Y')&&!adminRole.equals('N')){
            return ResultFactory.getFailResult("Unable to store the user: adminRole must be Y or N");
        }

        username = username.trim();
        email = email.trim();

        User user = userRepo.findOne(username);

        User testByEmailUser = userRepo.findByEmail(email);

        if(user == null){


            if(testByEmailUser == null){

                user = new User();
                user.setUsername(username);
                user.setEmail(email);

            } else {

                return ResultFactory.getFailResult("Unable to add new user: email address is already in use");

            }

        } else {

            if(testByEmailUser == null){

                user.setEmail(email);

            } else if (!user.equals(testByEmailUser)){

                return ResultFactory.getFailResult("The email address is already in use by username="
                        + testByEmailUser.getEmail()
                        + " and cannot be assigned to " + username);

            }

        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setAdminRole(adminRole);

        userRepo.save(user);

        return ResultFactory.getSuccessResult(user);

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<User> remove(String username, String actionUsername) {

        User actionUser = userRepo.findOne(actionUsername);

        if(!actionUser.isAdmin()){

            return ResultFactory.getFailResult(USER_NOT_ADMIN);

        }

        if(actionUsername.equalsIgnoreCase(username)){

            return ResultFactory.getFailResult("It is not allowed to delete yourself!");

        }

        if(username == null ){

            return ResultFactory.getFailResult("Unable to remove null user");

        }

        User affectedUser = userRepo.findOne(username);
        long taskLogCount = taskLogRepo.countByUser(affectedUser);

        if(affectedUser==null){

            return ResultFactory.getFailResult("Unable to obtain user [ " + username + " ] for deletion");

        } else if(taskLogCount > 0)  {

                return ResultFactory.getFailResult("User has task logs");

        } else{

                userRepo.delete(affectedUser);
                String msg = "User " + username + " was deleted by " + actionUsername;
                logger.info(msg);
                return ResultFactory.getSuccessResult(msg);

        }

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<User> find(String username, String actionUsername) {

        if(isValidUser(actionUsername)){

            User user = userRepo.findOne(username);
            return ResultFactory.getSuccessResult(user);

        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<List<User>> findAll(String actionUsername) {

        if(isValidUser(actionUsername)){

            return ResultFactory.getSuccessResult(userRepo.findAll());

        } else {

            return ResultFactory.getFailResult(USER_INVALID);

        }

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<User> findByUsernamePassword(String username, String password) {

        User user = userRepo.findByUsernameAndPassword(username,password);

        if (user == null){

            return ResultFactory.getFailResult("Unable to verify user/password combination");

        }   else {

            return ResultFactory.getSuccessResult(user);

        }

    }
}
