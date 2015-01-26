package ke.co.turbosoft.tt.service;

import java.util.List;

import ke.co.turbosoft.tt.entity.Task;
import ke.co.turbosoft.tt.vo.Result;

public interface TaskService {
	
	public Result<Task> store(
			Integer idTask,
			Integer idProject,
			String taskName,
			String actionUsername);
	public Result<Task> remove(Integer idTask,String actionUsername);
	public Result<Task> find(Integer idTask,String actionUsername);
	public Result<List<Task>> findAll(String actionUsername);

}
