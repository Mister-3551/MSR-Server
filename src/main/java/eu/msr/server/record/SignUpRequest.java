package eu.msr.server.record;

import eu.msr.server.security.validator.anno.*;

@ValidPassword
public record SignUpRequest(
        @ValidFullName
        String fullName,
        @ValidUsername
        String username,
        @ValidEmailAddress
        String emailAddress,
        String password,
        String confirmPassword,
        @ValidBirthdate
        String birthdate,
        @ValidCountry
        String country) {
}