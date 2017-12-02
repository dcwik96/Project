package pl.iledasz.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.iledasz.entities.AdvertPhoto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO {

    private Long id;
    private String title;
    private String description;
    private List<AdvertPhoto> photos;

}
