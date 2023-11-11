package eu.msr.server.security.validator;

import eu.msr.server.security.impl.ConstraintViolation;
import eu.msr.server.security.validator.anno.ValidMap;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MapValidator implements ConstraintValidator<ValidMap, MultipartFile>, ConstraintViolation {

    private String lengthMessage;
    private String charactersMessage;
    
    private  final String[] imageExtensions = {"tmx"};

    @Override
    public void initialize(ValidMap constraintAnnotation) {
        this.lengthMessage = constraintAnnotation.lengthMessage();
        this.charactersMessage = constraintAnnotation.charactersMessage();
    }

    @Override
    public boolean isValid(MultipartFile map, ConstraintValidatorContext constraintValidatorContext) {

        if (map.getSize() > 2097152L) {
            constraintViolation(constraintValidatorContext, lengthMessage);
        }

        if (map.getOriginalFilename().contains("..")) {
            constraintViolation(constraintValidatorContext, charactersMessage);
        }
        return fileExtensions(getImageExtension(map.getOriginalFilename()));
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }

    private boolean fileExtensions(String imageExtension) {
        for (String extension : imageExtensions) {
            if (extension.matches(imageExtension)) {
                return true;
            }
        }
        return false;
    }

    public String getImageExtension(String imageName) {
        if (imageName == null) {
            return null;
        }
        String[] fileNameParts = imageName.split("\\.");
        return fileNameParts[fileNameParts.length - 1];
    }
}