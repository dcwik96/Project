package pl.iledasz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.iledasz.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
