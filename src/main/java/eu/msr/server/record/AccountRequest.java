package eu.msr.server.record;

import eu.msr.server.security.validator.anno.ValidFullName;
import eu.msr.server.security.validator.anno.ValidImage;
import org.springframework.web.multipart.MultipartFile;

public record AccountRequest(
        @ValidImage
        MultipartFile image,
        @ValidFullName
        String fullName) {
}
