package pl.iledasz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.iledasz.entities.Photo;

@Repository
public interface PhotoReposiory extends JpaRepository<Photo, Long> {

    Photo findById(Long id);
    Photo findByFilename(String filename);

}
