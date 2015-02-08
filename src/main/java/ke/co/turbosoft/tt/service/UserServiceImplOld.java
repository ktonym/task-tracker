package ke.co.turbosoft.tt.service;

import ke.co.turbosoft.tt.entity.User;
import ke.co.turbosoft.tt.repo.TaskLogRepo;
import ke.co.turbosoft.tt.repo.UserRepo;
import ke.co.turbosoft.tt.vo.Result;
import ke.co.turbosoft.tt.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ktonym on 1/25/15.
 */

@Transactional
@Service("userServiceOld")
public class UserServiceImplOld extends AbstractService implements UserService {

    @Autowired
    protected TaskLogRepo taskLogRepo;


    public UserServiceImplOld() {
        super();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<User> store(String username,
                              String firstName,
                              String lastName,
                              String email,
                              String password,
                              Character adminRole,
                              String actionUsername) {


        if (actionUsername==null||"".equals(actionUsername)){
            return ResultFactory.getFailResult("Cannot verify identity of user");
        }

        User actionUser = userRepo.findOne(actionUsername);

        if (username == null || username.trim().isEmpty()
                || email == null || email.trim().isEmpty()){
            return ResultFactory.getFailResult("Unable to store a user " +
                    "without a valid non empty username/email");
        }

        if(password == null || password.length() == 0){
            return ResultFactory.getFailResult("Unable to store a user without a valid non empty password");
        }

        if (!adminRole.equals('Y') && !adminRole.equals('N')){
            return ResultFactory.getFailResult("Unable to store the user: adminRole must be Y or N");
        }

        username = username.trim();
        email = email.trim();

        User user = userRepo.findOne(username);

        // see if the user can be found by email
        User testByEmailUser = userRepo.findByEmail(email);
       // boolean doInsert = false;

        if(user == null){

            // TODO remove this debug tool
            System.out.println("User is null!");

            // user with this username is not yet in system
            if(testByEmailUser == null){

                user = new User();

                if(!actionUser.isAdmin()) {
                    return ResultFactory.getFailResult(USER_NOT_ADMIN);
                }
                user.setUsername(username);
                user.setAdminRole(adminRole);
            }  else {
                return ResultFactory.getFailResult("Unable to add new user: the email address is already in use");
            }

        }else{

            if(testByEmailUser == null){
                user.setEmail(email);
            } else if(!user.equals(testByEmailUser)){
                return ResultFactory.getFailResult("The email address is already in use by username="
                        + testByEmailUser.getUsername()
                        + " and cannot be assigned to " + username);
            }


            if (username != actionUsername && !actionUser.isAdmin()){
                return ResultFactory.getFailResult("Only an administrator can update another user!");
            }


            if(actionUser.isAdmin()){
                user.setAdminRole(adminRole);
            }

        }

        //todo remove the following line after debugging
        System.out.println("about to set properties of user inside userService");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);

        userRepo.save(user);

        String msg = "User " + username + " saved successfully by " + actionUsername;

        //todo remove after debugging..

        System.out.println("About to call ResultFactory");

        return ResultFactory.getSuccessResult(user,msg);


    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<User> remove(String username, String actionUsername) {

        User actionUser = userRepo.findOne(actionUsername);
        User affectedUser = userRepo.findOne(username);
        long taskLogCount = taskLogRepo.countByUser(affectedUser);

        if(!actionUser.isAdmin()){
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }

        if(affectedUser==null){
            return ResultFactory.getFailResult("Unable to obtain user [ " + username + " ] for deletion");
        }  else if (actionUser.equals(affectedUser)){
            return ResultFactory.getFailResult("Cannot delete oneself!");
        }   else{
            if(taskLogCount > 0)  {
                return ResultFactory.getFailResult("User has task logs");
            }   else{
                userRepo.delete(affectedUser);
                String msg = "User " + username + " was deleted by " + actionUsername;
                logger.info(msg);
                return ResultFactory.getSuccessResult(msg);
            }
        }

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<User> find(String username, String actionUsername) {
        if(isValidUser(actionUsername)){
            User user = userRepo.findOne(username);
            return ResultFactory.getSuccessResult(user);
        }   else{
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<List<User>> findAll(String actionUsername) {

        if(actionUsername==null || "".equals(actionUsername)){
            return ResultFactory.getFailResult(USER_INVALID);
        }

        User actionUser = userRepo.findOne(actionUsername);

        if(actionUser == null ){
            return ResultFactory.getFailResult("Unable to verify username [" + actionUsername + "]");
        }  else if (actionUser.isAdmin()){
            List<User> users = userRepo.findAll();
            return ResultFactory.getSuccessResult(users);
        } else {
            return ResultFactory.getFailResult("Only an admin user can view users");
        }


    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<User> findByUsernamePassword(String username, String password) {

        if(username==null||password==null){
            return ResultFactory.getFailResult("Username and password must be supplied");
        }

        User user = userRepo.findOne(username);

        if(user==null){
            return ResultFactory.getFailResult("Unable to verify username/password combination");
        }  else if(!user.getPassword().equals(password)){
            return ResultFactory.getFailResult("Unable to verify username/password combination");
        }  else{
               return ResultFactory.getSuccessResult(user);
        }

    }
}
