package pl.iledasz.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "select_offer")
@NoArgsConstructor
@Getter
@Setter
public class SelectOffer {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "advert_id")
    private Advertisement advertisement;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "expired_date", nullable = false)
    @Type(type = "org.hibernate.type.OffsetDateTimeType")
    private OffsetDateTime expiredDate;
}
