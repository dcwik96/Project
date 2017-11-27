package pl.iledasz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.iledasz.entities.AppUser;


public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    public AppUser findOneByLogin(String login);
}
