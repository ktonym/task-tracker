package ke.co.turbosoft.tt.repo;

import ke.co.turbosoft.tt.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
   User findByEmail(String email);
   User findByUsernameAndPassword(String username, String password);
}
