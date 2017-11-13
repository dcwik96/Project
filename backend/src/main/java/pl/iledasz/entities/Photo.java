package pl.iledasz.entities;

import javax.persistence.*;

@Entity
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "advert_id", referencedColumnName = "id")
    private Advertisement advertisement;

    public Photo() {
    }

    public Photo(byte[] image, Advertisement advertisement) {
        this.image = image;
        this.advertisement = advertisement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }
}
