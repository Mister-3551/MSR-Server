package eu.msr.server.record;

import eu.msr.server.security.validator.anno.ValidPassword;

@ValidPassword
public record NewPassword(
        String password,
        String confirmPassword) {
}