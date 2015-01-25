package ke.co.turbosoft.tt.service;

import java.time.LocalDate;
import java.util.List;

import ke.co.turbosoft.tt.entity.TaskLog;
import ke.co.turbosoft.tt.vo.Result;

public interface TaskLogService {
	
	public Result<TaskLog> store(
			Integer idTaskLog,
			Integer idTask,
			String username,
			String taskDescription,
			LocalDate taskLogDate,
			int taskMinutes,
			String actionUsername);
	
	public Result<TaskLog> remove(Integer idTaskLog,
			String actionUsername);
	
	public Result<TaskLog> find(Integer idTaskLog, String actionUsername);
	
	public Result<List<TaskLog>> findByUser(String username, LocalDate startDate, LocalDate endDate, String actionUsername);
}
