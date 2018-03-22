package pl.iledasz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.iledasz.entities.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Photo findByAdvertphoto_Id(Long id);

}
