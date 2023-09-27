package eu.msr.server.record;

import eu.msr.server.security.validator.anno.ValidEmailAddress;
import eu.msr.server.security.validator.anno.ValidFullName;
import lombok.NonNull;

public record ContactRequest(

        @ValidFullName
        String fullName,
        @ValidEmailAddress
        String emailAddress,
        @NonNull
        String subject,
        @NonNull
        String message) {
}
