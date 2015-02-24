package ke.co.turbosoft.tt.repo;

import ke.co.turbosoft.tt.entity.Project;
import ke.co.turbosoft.tt.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Integer> {
    List<Task> findByProject(Project project);
}
