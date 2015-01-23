package ke.co.turbosoft.tt.repo;

import ke.co.turbosoft.tt.entity.TaskLog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskLogRepo extends JpaRepository<TaskLog, Long> {

}
