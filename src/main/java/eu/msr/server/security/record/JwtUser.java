package eu.msr.server.security.record;

import java.util.Set;

public record JwtUser(
        String fullName,
        String username,
        String emailAddress,
        String country,
        boolean accountConfirmed,
        boolean accountLocked,
        Set<String> authorities) {
}