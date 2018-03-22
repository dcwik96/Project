package pl.iledasz.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "advert")
@NoArgsConstructor
@Getter
@Setter
public class Advertisement {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @Column(name = "start_date", nullable = false)
    @Type(type = "org.hibernate.type.OffsetDateTimeType")
    private OffsetDateTime startDate;

    @Column(name = "duration", nullable = false)
    private Long duration;

    @Column(name = "end_date", nullable = false)
    @Type(type = "org.hibernate.type.OffsetDateTimeType")
    private OffsetDateTime endDate;

    @Column(name = "available")
    private boolean available;

    @OneToMany(mappedBy = "advertisement")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Offer> offers;


    @OneToMany(mappedBy = "advertisement")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<AdvertPhoto> photos;

}
