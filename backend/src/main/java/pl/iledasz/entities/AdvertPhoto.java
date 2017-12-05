package pl.iledasz.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "advertphoto")
@NoArgsConstructor
@Getter
@Setter
public class AdvertPhoto {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "advert_id")
    private Advertisement advertisement;

    @Column(name = "description")
    private String Description;

    public AdvertPhoto(Advertisement advertisement, String description) {
        this.advertisement = advertisement;
        this.Description = description;
    }

}
