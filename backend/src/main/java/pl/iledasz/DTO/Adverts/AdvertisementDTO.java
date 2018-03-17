package pl.iledasz.DTO.Adverts;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.iledasz.DTO.AdvertPhotoDTO;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDTO {

    private Long id;
    private String title;
    private String description;
    private OffsetDateTime startDate;
    private Long duration;
    private OffsetDateTime endDate;
    private List<AdvertPhotoDTO> photos;

}
