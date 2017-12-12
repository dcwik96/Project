package pl.iledasz.validator;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.iledasz.DTO.NewAdvertDTO;

@Component
public class NewAdvertValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        NewAdvertDTO advert = (NewAdvertDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty", "Tytuł nie moze byc pusty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty", "Opis nie moze byc pusty!");

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "imagesDescriptions", "NotEmpty", "Opis  zdjęcia nie może byc pusty!");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "images", "NotEmpty", "Przynajmniej jedno zdjęcie!");

        if(!errors.hasErrors())
        {
            if(advert.getImagesDescriptions().size() != advert.getImages().size())
                errors.rejectValue("images","Liczba opisów zdjęć nie odpowiada liczbie zdjęć");
        }
    }
}
