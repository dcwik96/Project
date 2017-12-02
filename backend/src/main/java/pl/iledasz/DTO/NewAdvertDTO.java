package pl.iledasz.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewAdvertDTO {


    //Advertisement elements
    private String title;
    private String description;

    //Photo-Advert elements
    private String photo_description;

    //Photo elements
    private byte[] image;
}
