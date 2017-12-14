package pl.iledasz.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewAdvertDTO {


    //Advertisement elements
    private String title;
    private String description;
    private Long duration;

    //Photo-Advert elements
    private List <String> imagesDescriptions;
    //Photo elements
    private List<MultipartFile> images;

    public TreeMap<String, MultipartFile > getPhotosWithDescriptions()
    {
        TreeMap <String, MultipartFile > imagesWithDescriptions = new TreeMap<>();

        Iterator<String>imageDescriptionIterator = imagesDescriptions.iterator();
        Iterator<MultipartFile> imageIterator = images.iterator();

        while (imageDescriptionIterator.hasNext() && imageIterator.hasNext()) {
            imagesWithDescriptions.put(imageDescriptionIterator.next(),imageIterator.next());
        }

        return imagesWithDescriptions;
    }
}
