package ke.co.turbosoft.tt.service;

import ke.co.turbosoft.tt.entity.Task;
import ke.co.turbosoft.tt.entity.TaskLog;
import ke.co.turbosoft.tt.entity.User;
import ke.co.turbosoft.tt.repo.TaskLogRepo;
import ke.co.turbosoft.tt.repo.TaskRepo;
import ke.co.turbosoft.tt.vo.Result;
import ke.co.turbosoft.tt.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by ktonym on 1/25/15.
 */
@Transactional
@Service("taskLogService")
public class TaskLogServiceImpl extends AbstractService implements TaskLogService {

    @Autowired
    protected TaskLogRepo taskLogRepo;
    @Autowired
    protected TaskRepo taskRepo;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<TaskLog> store(Integer idTaskLog,
                                 Integer idTask,
                                 String username,
                                 String taskDescription,
                                 LocalDate taskLogDate,
                                 int taskMinutes,
                                 String actionUsername) {

        User actionUser = userRepo.findOne(actionUsername);
        User taskUser = userRepo.findOne(username);

        if(actionUser == null || taskUser == null){
            return ResultFactory.getFailResult(USER_INVALID);
        }

        Task task = taskRepo.findOne(idTask);

        if(task==null){
            return ResultFactory.getFailResult("Unable to store task log with null task");
        }

        if( !actionUser.isAdmin() && !taskUser.equals(actionUser)){
            return ResultFactory.getFailResult("User performing save must be an admin user or saving their own record");
        }

        TaskLog taskLog;

        if(idTaskLog==null){
            taskLog = new TaskLog();
        }  else{
            taskLog = taskLogRepo.findOne(idTaskLog);
            if(taskLog==null){
                return ResultFactory.getFailResult("Unable to find taskLog instance with ID=" + idTaskLog);
            }
        }

        taskLog.setTask(task);
        taskLog.setTaskDescription(taskDescription);
        taskLog.setTaskLogDate(taskLogDate);
        taskLog.setUser(taskUser);
        taskLog.setTaskMinutes(taskMinutes);

        taskLogRepo.save(taskLog);

        return ResultFactory.getSuccessResult(taskLog);

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<TaskLog> remove(Integer idTaskLog, String actionUsername) {

        User actionUser = userRepo.findOne(actionUsername);

        if(actionUser == null ){
            return ResultFactory.getFailResult(USER_INVALID);
        }

        if(idTaskLog==null){
            return ResultFactory.getFailResult("Unable to remove TaskLog [null idTaskLog]");
        }

        TaskLog taskLog = taskLogRepo.findOne(idTaskLog);

        if(taskLog == null) {
            return ResultFactory.getFailResult("Unable to load TaskLog for removal with idTaskLog=" + idTaskLog);
        }

        // only the user that owns the task log may remove it
        // or an admin user

        if(actionUser.isAdmin() ||
            taskLog.getUser().equals(actionUser)){
            taskLogRepo.delete(taskLog);
            return ResultFactory.getSuccessResult("taskLog removed successfully");
        } else{
            return ResultFactory.getFailResult("Only an admin user or task log owner can delete a task log");
        }

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<TaskLog> find(Integer idTaskLog, String actionUsername) {

        User actionUser=userRepo.findOne(actionUsername);

        if(actionUser==null){
              return ResultFactory.getFailResult(USER_INVALID);
        }

        TaskLog taskLog = taskLogRepo.findOne(idTaskLog);

        if (taskLog==null){
            return ResultFactory.getFailResult("Task log not found with idTaskLog=" + idTaskLog);
        } else if (actionUser.isAdmin() || taskLog.getUser().equals(actionUser)){
            return ResultFactory.getSuccessResult(taskLog);
        }  else {
            return ResultFactory.getFailResult("User does not have permission to view this task log");
        }

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<List<TaskLog>> findByUser(String username,
                                            LocalDate startDate,
                                            LocalDate endDate,
                                            String actionUsername) {


        User actionUser = userRepo.findOne(actionUsername);
        User taskUser = userRepo.findOne(username);

        if(taskUser==null || actionUser==null){
            return ResultFactory.getFailResult(USER_INVALID);
        }

        if(startDate==null||endDate==null){
            return ResultFactory.getFailResult("Start and end date are required for findByUser");
        }

        if(actionUser.isAdmin() || taskUser.equals(actionUser)){
            List<TaskLog> taskLogs = taskLogRepo.findByUserAndDateBetween(taskUser,startDate,endDate);
            return ResultFactory.getSuccessResult(taskLogs);
        }   else{
            return ResultFactory.getFailResult("Unable to find task logs. User does not have permission with username=" + username);
        }

    }


}
