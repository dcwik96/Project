package pl.iledasz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.iledasz.entities.ChosenOffer;

@Repository
public interface SelectOfferRepository extends JpaRepository<ChosenOffer, Long> {
}
