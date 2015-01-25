package ke.co.turbosoft.tt.repo;

import ke.co.turbosoft.tt.entity.Task;
import ke.co.turbosoft.tt.entity.TaskLog;

import ke.co.turbosoft.tt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskLogRepo extends JpaRepository<TaskLog, Integer> {

     long CountByTask(Task task);
     List<TaskLog> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);

}
