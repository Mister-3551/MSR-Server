package eu.msr.server.service;

import eu.msr.server.entity.User;
import eu.msr.server.record.ContactRequest;
import eu.msr.server.record.EmailAddressRequest;
import eu.msr.server.record.SignUpRequest;
import eu.msr.server.repository.ContactRepository;
import eu.msr.server.repository.UsersRepository;
import eu.msr.server.service.email.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailService {

    private final TokenService tokenService;
    private final ContactRepository contactRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public EmailService(TokenService tokenService, ContactRepository contactRepository, UsersRepository usersRepository) {
        this.tokenService = tokenService;
        this.contactRepository = contactRepository;
        this.usersRepository = usersRepository;
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

    public void confirm(SignUpRequest signUpRequest, String generatedToken) throws IOException {
        final String sender = "confirm@memostickrescue.eu.org";
        final String password = "Fo3m2jPiF^i3w7%6A47j";

        String subject = "Confirm Email address";

        String fullName = replaceSpecialCharacters(signUpRequest.fullName());
        String token = replaceSpecialCharacters(generatedToken);

        String body = new String(ClassLoader.getSystemResourceAsStream("email-templates/Confirm.html").readAllBytes(), StandardCharsets.UTF_8)
                .replace("{{name}}", fullName)
                .replace("{{token}}", token);

        Sender.send(sender, password, signUpRequest.emailAddress(), subject, body);
    }

    public String resetPassword(EmailAddressRequest emailAddressRequest) throws IOException {
        final String sender = "password@memostickrescue.eu.org";
        final String password = "79OCuG^v422%T!23fldC";

        Optional<User> userBy = usersRepository.findByUsernameOrEmailAddress(emailAddressRequest.emailAddress());
        if (!userBy.isPresent()) {
            return "User with this email address can not be found";
        }

        User user = userBy.get();
        if (user.getPasswordResetTimer() != null && Duration.between(user.getPasswordResetTimer(), LocalDateTime.now()).compareTo(Duration.ofMinutes(30L)) <= 0 && user.getPasswordResetTimer().isAfter(LocalDateTime.now())) {
            return "Time till next allowed attempt: " +
                    Duration.between(LocalDateTime.now(), user.getPasswordResetTimer()).toMinutes()
                    + " min and " +
                    (Duration.between(LocalDateTime.now(), user.getPasswordResetTimer()).toSeconds() % 60) + " seconds";
        } else {
            usersRepository.setPasswordResetTimer(user.getEmailAddress());
        }

        String subject = "Change Password";

        String fullName = replaceSpecialCharacters(user.getFullName());
        String token = replaceSpecialCharacters(tokenService.generateEmailToken(emailAddressRequest.emailAddress(), "CHANGE_EMAIL"));

        String body = new String(ClassLoader.getSystemResourceAsStream("email-templates/ChangePassword.html").readAllBytes(), StandardCharsets.UTF_8)
                .replace("{{name}}", fullName)
                .replace("{{token}}", token);

        return Sender.send(sender, password, emailAddressRequest.emailAddress(), subject, body);
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