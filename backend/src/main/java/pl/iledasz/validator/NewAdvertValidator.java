package pl.iledasz.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.iledasz.DTO.NewAdvertDTO;

@Component
public class NewAdvertValidator implements Validator {

    private static final String defaultMessageOfNullTitle = "Tytuł nie moze byc pusty!";
    private static final String defaultMessageOfNullDescription = "Opis nie moze byc pusty!";
    private static final String defaultMessageOfNullDuration = "Czas trwania oferty nie moze byc pusty!";
    private static final String defaultMessageOfNullImages = "Przynajmniej jedno zdjęcie!";
    private static final String defaultMessageOfNullImageDescription = "Opis  zdjęcia nie może byc pusty!";
    private static final String defaultMessageOfNotEqualsImageAndImageDescription = "Liczba opisów zdjęć nie odpowiada liczbie zdjęć";

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        NewAdvertDTO advert = (NewAdvertDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty", defaultMessageOfNullTitle);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty", defaultMessageOfNullDescription);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "duration", "NotEmpty", defaultMessageOfNullDuration);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "images", "NotEmpty", defaultMessageOfNullImages);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "imagesDescriptions", "NotEmpty", defaultMessageOfNullImageDescription);

        if (!errors.hasErrors()) {
            if (advert.getImagesDescriptions().size() != advert.getImages().size())
                errors.rejectValue("images", defaultMessageOfNotEqualsImageAndImageDescription);
        }
    }
}
