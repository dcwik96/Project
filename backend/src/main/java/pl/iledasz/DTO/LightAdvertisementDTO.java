package pl.iledasz.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LightAdvertisementDTO {

    private Long id;
    private String title;
    private AdvertPhotoDTO photo;
}
