package pl.iledasz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.iledasz.entities.ChosenOffer;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface ChosenOfferRepository extends JpaRepository<ChosenOffer, Long> {

    List <ChosenOffer> findAllByOffer_AppUser_LoginAndApprovedIsNullAndExpiredDateAfter(String login, OffsetDateTime now);
    ChosenOffer findByOffer_AppUser_LoginAndIdAndApprovedIsNullAndExpiredDateAfter(String login, Long id, OffsetDateTime now);
}
