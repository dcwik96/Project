package pl.iledasz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.iledasz.entities.Advertisement;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    Advertisement findOneById(Long id);
    @Query(value="SELECT * FROM Advert ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Advertisement randomOne();
    List<Advertisement> findAllByEndDateAfterAndAndAvailableTrueOrderByEndDateAsc(OffsetDateTime endDate);
}
