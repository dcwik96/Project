package pl.iledasz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.AppUser;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    Advertisement findOneById(Long id);
    Advertisement findAdvertisementsByAppUser_LoginAndId(long id, String login);
    Advertisement findAdvertisementByAppUser_LoginNotLikeAndId(String login, long id);
    @Query(value="SELECT * FROM Advert ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Advertisement randomOne();
    List<Advertisement> findAllByEndDateAfterAndAndAvailableTrueOrderByEndDateAsc(OffsetDateTime endDate);
    List<Advertisement> findAllByAppUser(AppUser appUser);
    Advertisement findAdvertisementsByAppUserAndId(AppUser appUser, Long id);


}
