package pl.iledasz.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.iledasz.app.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
}
