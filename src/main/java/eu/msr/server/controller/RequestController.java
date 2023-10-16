package eu.msr.server.controller;

import eu.msr.server.record.ContactRequest;
import eu.msr.server.record.EmailAddressRequest;
import eu.msr.server.record.NewPassword;
import eu.msr.server.record.TokenRequest;
import eu.msr.server.security.impl.ValidationException;
import eu.msr.server.service.AuthService;
import eu.msr.server.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RequestController implements ValidationException {

    private final EmailService emailService;
    private final AuthService authService;

    @Autowired
    public RequestController(EmailService emailService, AuthService authService) {
        this.emailService = emailService;
        this.authService = authService;
    }

    @PostMapping(value = "/contact")
    public String contact(@Valid @RequestBody ContactRequest contactRequest) throws IOException {
        return emailService.contact(contactRequest);
    }

    @PostMapping(value = "/reset")
    public String resetPassword(@Valid @RequestBody EmailAddressRequest emailAddressRequest) throws IOException {
        return emailService.resetPassword(emailAddressRequest);
    }

    @PostMapping(value = "/confirm")
    public String confirmEmailAddress(Authentication authentication, @RequestBody TokenRequest tokenRequest) {
        return authService.confirmEmailAddress(authentication, tokenRequest);
    }

    @PostMapping(value = "/new-password")
    public String newPassword(Authentication authentication, @Valid @RequestBody NewPassword newPassword) {
        return authService.newPassword(authentication, newPassword);
    }
}