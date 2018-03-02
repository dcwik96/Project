package pl.iledasz.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChosenOfferDTO {


    private Long id;

    private LightAdvertisementDTO advertisement;

    private OfferDTO offer;

    private Boolean approved;

    private OffsetDateTime expiredDate;
}
