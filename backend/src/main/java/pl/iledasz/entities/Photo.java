package pl.iledasz.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "photo")
@Getter
@Setter
@NoArgsConstructor
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


    public Photo(byte[] image, AdvertPhoto advertphoto) {
        this.image = image;
        this.advertphoto = advertphoto;
    }

    public Long getId() {
        return id;
    }

}


