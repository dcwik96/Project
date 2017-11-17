package pl.iledasz.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "image", nullable = false)
    private byte[] image;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "advertphoto_id")
    private AdvertPhoto advertphoto;

    public Photo() {
    }

    public Photo(byte[] image, AdvertPhoto advertphoto) {
        this.image = image;
        this.advertphoto = advertphoto;
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

    public AdvertPhoto getAdvertphoto() {
        return advertphoto;
    }

    public void setAdvertphoto(AdvertPhoto advertphoto) {
        this.advertphoto = advertphoto;
    }
}


