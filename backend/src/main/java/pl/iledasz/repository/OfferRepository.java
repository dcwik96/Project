package pl.iledasz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.iledasz.entities.Offer;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByAdvertisement_Id(Long id);

    Offer findOneById(Long id);

    Offer findOfferByAdvertisement_IdAndAppUser_Login(long id, String login);
    Offer findOfferByIdAndAdvertisement_AppUser_Login(long id, String login);
    Offer findOfferByIdAndAdvertisement_AppUser_LoginAndAdvertisement_AvailableTrue(long id, String login);

}
