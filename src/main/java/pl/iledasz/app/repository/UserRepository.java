package pl.iledasz.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.iledasz.app.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{
}
