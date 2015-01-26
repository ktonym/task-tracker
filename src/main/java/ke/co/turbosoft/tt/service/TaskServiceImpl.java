package ke.co.turbosoft.tt.service;

import ke.co.turbosoft.tt.entity.Project;
import ke.co.turbosoft.tt.entity.Task;
import ke.co.turbosoft.tt.entity.User;
import ke.co.turbosoft.tt.repo.ProjectRepo;
import ke.co.turbosoft.tt.repo.TaskLogRepo;
import ke.co.turbosoft.tt.repo.TaskRepo;
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
@Service("taskService")
public class TaskServiceImpl extends AbstractService implements TaskService {

    @Autowired
    protected TaskRepo taskRepo;

    @Autowired
    protected TaskLogRepo taskLogRepo;

    @Autowired
    protected ProjectRepo projectRepo;

    public TaskServiceImpl() {
        super();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<Task> store(Integer idTask, Integer idProject, String taskName, String actionUsername) {

        User actionUser = userRepo.findOne(actionUsername);

        if(!actionUser.isAdmin()){
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }

        Project project = projectRepo.findOne(idProject);

        if(project==null){
            return ResultFactory.getFailResult("Unable to store task without a valid project=" +
            idProject + "]");
        }

        Task task;

        if(idTask==null){
            task = new Task();
            task.setProject(project);
            project.getTasks().add(task);
        } else {
            task=taskRepo.findOne(idTask);
            if(task==null){
                return ResultFactory.getFailResult("Unable to find task instance with idTask="+idTask);
            }  else{
                if(! task.getProject().equals(project)){

                    Project currentProject = task.getProject();
                    // reassign to new project
                    task.setProject(project);
                    project.getTasks().add(task);
                    // remove from previous project
                    currentProject.getTasks().remove(task);

                }
            }
        }

        task.setName(taskName);

        taskRepo.save(task);

        return ResultFactory.getSuccessResult(task);

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<Task> remove(Integer idTask, String actionUsername) {

        User actionUser = userRepo.findOne(actionUsername);

        if(!actionUser.isAdmin()){
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }

        if(idTask==null){
            return ResultFactory.getFailResult("Unable to remove Task [null idTask]");
        } else {

            Task task = taskRepo.findOne(idTask);
            long taskLogCount = taskLogRepo.countByTask(task);

            if(task==null){
                return ResultFactory.getFailResult("Unable to load Task for removal with idTask=" + idTask);
            } else if(taskLogCount > 0){
                return ResultFactory.getFailResult("Unable to remove Task with idTask=" + idTask + " as valid" +
                        "task logs are assigned");
            } else {

                Project project = task.getProject();

                taskRepo.delete(task);

                project.getTasks().remove(task);

                String msg = "Task " + task.getName() + " was deleted by " + actionUsername;

                logger.info(msg);

                return ResultFactory.getSuccessResult(msg);

            }


        }
    }

    @Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
    @Override
    public Result<Task> find(Integer idTask, String actionUsername) {

        if(isValidUser(actionUsername)){
             return ResultFactory.getSuccessResult(taskRepo.findOne(idTask));
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<List<Task>> findAll(String actionUsername) {
        if(isValidUser(actionUsername)){
            List<Task> taskList =  taskRepo.findAll();
           return ResultFactory.getSuccessResult(taskList);
        }    else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }
}
