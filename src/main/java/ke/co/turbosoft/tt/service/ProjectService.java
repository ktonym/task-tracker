package ke.co.turbosoft.tt.service;

import java.util.List;

import ke.co.turbosoft.tt.entity.Project;
import ke.co.turbosoft.tt.vo.Result;

public interface ProjectService {
	
	public Result<Project> store(
			Integer idProject,
			Integer idCompany,
			String projectName,
			String actionUsername);
	
	public Result<Project> remove(Integer idProject,String actionUsername);
	public Result<Project> find(Integer idProject,String actionUsername);
	public Result<List<Project>> findAll(String actionUsername);
	
}
