package eu.msr.server.controller;

import eu.msr.server.record.ContactRequest;
import eu.msr.server.security.impl.ValidationException;
import eu.msr.server.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RequestController implements ValidationException {

    private final EmailService emailService;

    @Autowired
    public RequestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "/contact")
    public String contact(@Valid @RequestBody ContactRequest contactRequest) throws IOException {
        return emailService.contact(contactRequest);
    }
}