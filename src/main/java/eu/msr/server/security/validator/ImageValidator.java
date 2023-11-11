package eu.msr.server.security.validator;

import eu.msr.server.security.impl.ConstraintViolation;
import eu.msr.server.security.validator.anno.ValidFullName;
import eu.msr.server.security.validator.anno.ValidImage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageValidator implements ConstraintValidator<ValidImage, MultipartFile>, ConstraintViolation {

    private String lengthMessage;
    private String charactersMessage;
    
    private  final String[] imageExtensions = {"jpg", "png", "gif"};

    @Override
    public void initialize(ValidImage constraintAnnotation) {
        this.lengthMessage = constraintAnnotation.lengthMessage();
        this.charactersMessage = constraintAnnotation.charactersMessage();
    }

    @Override
    public boolean isValid(MultipartFile image, ConstraintValidatorContext constraintValidatorContext) {

        if (image.getSize() > 2097152L) {
            constraintViolation(constraintValidatorContext, lengthMessage);
        }

        if (image.getOriginalFilename().contains("..")) {
            constraintViolation(constraintValidatorContext, charactersMessage);
        }
        return imageExtensions(getImageExtension(image.getOriginalFilename()));
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }

    private boolean imageExtensions(String imageExtension) {
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