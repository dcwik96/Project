package pl.iledasz.DTO.Adverts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.iledasz.DTO.AdvertPhotoDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LightAdvertisementDTO {

    private Long id;
    private String title;
    private AdvertPhotoDTO photo;
}
