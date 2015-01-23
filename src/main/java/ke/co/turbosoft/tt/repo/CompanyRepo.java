package ke.co.turbosoft.tt.repo;

import ke.co.turbosoft.tt.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company, Long> {

}
