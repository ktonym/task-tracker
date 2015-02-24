package ke.co.turbosoft.tt.repo;

import ke.co.turbosoft.tt.entity.Company;
import ke.co.turbosoft.tt.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Integer> {
        List<Project> findByCompany(Company company);
}
