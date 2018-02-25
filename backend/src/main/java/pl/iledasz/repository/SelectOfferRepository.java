package pl.iledasz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.iledasz.entities.SelectOffer;

@Repository
public interface SelectOfferRepository extends JpaRepository<SelectOffer, Long> {
}
