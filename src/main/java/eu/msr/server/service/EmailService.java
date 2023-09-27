package eu.msr.server.service;

import eu.msr.server.MSRServerApplication;
import eu.msr.server.record.ContactRequest;
import eu.msr.server.repository.ContactRepository;
import eu.msr.server.service.email.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private final ContactRepository contactRepository;

    @Autowired
    public EmailService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public String contact(ContactRequest contactRequest) throws IOException {

        final String sender = "contact@memostickrescue.eu.org";
        final String password = "Eb#AS8EX2P9854p^M^n&";

        if (contactRepository.checkDuplicate(
                contactRequest.emailAddress(),
                contactRequest.subject(),
                contactRequest.message()) >= 1) {
            return "Your message has already been sent";
        }

        if (contactRepository.insertMessage(
                contactRequest.fullName(),
                contactRequest.emailAddress(),
                contactRequest.subject(),
                contactRequest.message()) == 1) {

            String fullName = replaceSpecialCharacters(contactRequest.fullName());
            String message = replaceSpecialCharacters(contactRequest.message());

            String body = new String(ClassLoader.getSystemResourceAsStream("email-templates/Contact.html").readAllBytes(), StandardCharsets.UTF_8)
                    .replace("{{name}}", fullName)
                    .replace("{{message}}", message);

            return Sender.send(sender, password, contactRequest.emailAddress(), contactRequest.subject(), body);
        }
        return "Something went wrong";
    }

    private String replaceSpecialCharacters(String string) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char chr : string.toCharArray()) {
            stringBuilder.append("&#");
            stringBuilder.append((int) chr);
            stringBuilder.append(";");
        }

        return stringBuilder.toString();
    }
}