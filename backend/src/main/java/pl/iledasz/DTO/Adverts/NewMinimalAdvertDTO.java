package pl.iledasz.DTO.Adverts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewMinimalAdvertDTO {

    //Advertisement elements
    private String title;
    private String description;
    private Long duration;
}
