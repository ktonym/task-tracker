package ke.co.turbosoft.tt.repo;

import ke.co.turbosoft.tt.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {

}
