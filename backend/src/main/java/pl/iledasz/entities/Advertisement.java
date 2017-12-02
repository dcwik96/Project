package pl.iledasz.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pl.iledasz.DTO.AdvertPhotoDTO;

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


    @OneToMany (mappedBy = "advertisement")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @Fetch(FetchMode.SELECT)
    private List<AdvertPhoto> photos;

    public Advertisement(String title, String description, AppUser appUser, List<AdvertPhoto> photos) {
        this.title = title;
        this.description = description;
        this.appUser = appUser;
        this.photos = photos;
    }
}
