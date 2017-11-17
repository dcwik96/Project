package pl.iledasz.entities;

import javax.persistence.*;

@Entity
@Table(name = "advertphoto")
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

    public AdvertPhoto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
