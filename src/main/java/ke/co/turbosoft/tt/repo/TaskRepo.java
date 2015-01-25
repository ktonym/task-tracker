package ke.co.turbosoft.tt.repo;

import ke.co.turbosoft.tt.entity.Task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Integer> {

}
