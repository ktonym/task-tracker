package ke.co.turbosoft.tt.service;

import ke.co.turbosoft.tt.entity.User;
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
@Service("userService")
public class UserServiceImpl extends AbstractService implements UserService {

    @Autowired
    protected UserRepo userRepo;

    public UserServiceImpl() {
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

        User user = userRepo.findOne(username);

        if(user==null){
            user = new User();

            if(!actionUser.isAdmin()) {
                return ResultFactory.getFailResult(USER_NOT_ADMIN);
            }

            user.setUsername(username);

        }


        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setAdminRole(adminRole);

        userRepo.save(user);

        String msg = "User " + username + " saved successfully by " + actionUsername;

        return ResultFactory.getSuccessResult(user,msg);


    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<User> remove(String username, String actionUsername) {

        User actionUser = userRepo.findOne(actionUsername);
        User affectedUser = userRepo.findOne(username);

        if(!actionUser.isAdmin()){
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }

        if(affectedUser==null){
            return ResultFactory.getFailResult("Unable to obtain user [ " + username + " ] for deletion");
        }  else if (actionUser.equals(affectedUser)){
            return ResultFactory.getFailResult("Cannot delete oneself!");
        }   else{
            if(affectedUser.getTasklogs().isEmpty())  {
                userRepo.delete(affectedUser);
                String msg = "User " + username + " successfully deleted by " + actionUsername;
                return ResultFactory.getSuccessResult(msg);
            }   else{
                return ResultFactory.getFailResult("User has task logs");
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
            return ResultFactory.getFailResult("Wrong credentials");
        }  else if(!user.getPassword().equals(password)){
            return ResultFactory.getFailResult("Wrong credentials");
        }  else{
               return ResultFactory.getSuccessResult(user);
        }

    }
}
