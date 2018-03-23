package pl.iledasz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.iledasz.entities.AdvertPhoto;

import java.util.List;

public interface AdvertPhotoRepository extends JpaRepository<AdvertPhoto, Long> {
    List<AdvertPhoto> findAdvertPhotosByAdvertisement_Id(long id);
}
