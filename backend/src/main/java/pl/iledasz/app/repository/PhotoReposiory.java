package pl.iledasz.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.iledasz.app.entities.Photo;

@Repository
public interface PhotoReposiory extends JpaRepository<Photo, Long> {

    Photo findById(Long id);
    Photo findByFilename(String filename);

}
