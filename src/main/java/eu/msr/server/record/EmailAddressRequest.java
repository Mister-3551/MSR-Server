package eu.msr.server.record;

import eu.msr.server.security.validator.anno.ValidEmailAddress;

public record EmailAddressRequest(
        @ValidEmailAddress
        String emailAddress) {
}