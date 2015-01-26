package ke.co.turbosoft.tt.service;

import ke.co.turbosoft.tt.entity.Company;
import ke.co.turbosoft.tt.entity.Project;
import ke.co.turbosoft.tt.entity.User;
import ke.co.turbosoft.tt.repo.CompanyRepo;
import ke.co.turbosoft.tt.repo.ProjectRepo;
import ke.co.turbosoft.tt.vo.Result;
import ke.co.turbosoft.tt.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ktonym on 1/26/15.
 */
@Transactional
@Service("projectService")
public class ProjectServiceImpl extends AbstractService implements ProjectService {

    @Autowired
    protected ProjectRepo projectRepo;

    @Autowired
    protected CompanyRepo companyRepo;

    public ProjectServiceImpl() {
        super();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<Project> store(Integer idProject, Integer idCompany, String projectName, String actionUsername) {

        User actionUser=userRepo.findOne(actionUsername);

        if(!actionUser.isAdmin()){
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }

        Company company = companyRepo.findOne(idCompany);

        if(company==null){
            return ResultFactory.getFailResult("Unable to store project without a valid company=" +
            idCompany + "]");
        }

        Project project;

        if (idProject==null){

            project = new Project();

            project.setCompany(company);

            company.getProjects().add(project);

        }  else {

            project = projectRepo.findOne(idProject);

            if(project==null){
                return ResultFactory.getFailResult("Unable to find project with ID [ " +idProject+" ]");
            }  else {
                if (! project.getCompany().equals(company)){
                    Company currentCompany = project.getCompany();
                    project.setCompany(company);
                    company.getProjects().add(project);
                    currentCompany.getProjects().remove(project);
                }

            }

        }

        project.setName(projectName);

        projectRepo.save(project);

        return ResultFactory.getSuccessResult(project);

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<Project> remove(Integer idProject, String actionUsername) {

        User actionUser = userRepo.findOne(actionUsername);

        if(!actionUser.isAdmin()){
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }

        if(idProject==null){
            return ResultFactory.getFailResult("Unable to remove " +
              "Project [null idProject]");
        }

        Project project = projectRepo.findOne(idProject);

        if(project==null){

            return ResultFactory.getFailResult("Unable to fetch project with ID [ "+idProject+ " ] " +
                    "for removal");

        } else{

            if(project.getTasks()==null || project.getTasks().isEmpty()){

                projectRepo.delete(project);

                String msg = "Project "+project.getName()+" was deleted by " +actionUser;

                logger.info(msg);

                return ResultFactory.getSuccessResult(msg);

            } else {

                return ResultFactory.getFailResult("Project has tasks and cannot be deleted");

            }
        }



    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<Project> find(Integer idProject, String actionUsername) {

        if(isValidUser(actionUsername)){

            Project project = projectRepo.findOne(idProject);

            return ResultFactory.getSuccessResult(project);

        }   else {

            return ResultFactory.getFailResult(USER_INVALID);

        }

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Result<List<Project>> findAll(String actionUsername) {

        if(isValidUser(actionUsername)){

            List<Project> projectList = projectRepo.findAll();

            return ResultFactory.getSuccessResult(projectList);

        } else {

            return ResultFactory.getFailResult(USER_INVALID);

        }

    }
}
