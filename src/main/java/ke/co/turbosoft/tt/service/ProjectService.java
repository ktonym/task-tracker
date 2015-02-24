package ke.co.turbosoft.tt.service;

import ke.co.turbosoft.tt.entity.Company;
import ke.co.turbosoft.tt.entity.Project;
import ke.co.turbosoft.tt.vo.Result;

import java.util.List;

public interface ProjectService {
	
	public Result<Project> store(
			Integer idProject,
			Integer idCompany,
			String projectName,
			String actionUsername);
	
	public Result<Project> remove(Integer idProject,String actionUsername);
	public Result<Project> find(Integer idProject,String actionUsername);
	public Result<List<Project>> findAll(String actionUsername);
    public Result<List<Project>> findByCompany(Company company, String actionUsername);
	
}
