package pl.iledasz.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "offer", nullable = false)
    private BigDecimal offer;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "advert_id")
    private Advertisement advertisement;

    public Offer(BigDecimal offer, AppUser appUser, Advertisement advertisement) {
        this.offer = offer;
        this.appUser = appUser;
        this.advertisement = advertisement;
    }
}
