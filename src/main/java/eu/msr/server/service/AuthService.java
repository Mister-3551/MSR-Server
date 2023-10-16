package eu.msr.server.service;

import eu.msr.server.entity.User;
import eu.msr.server.record.NewPassword;
import eu.msr.server.record.SignInRequest;
import eu.msr.server.record.SignUpRequest;
import eu.msr.server.record.TokenRequest;
import eu.msr.server.repository.UsersRepository;
import eu.msr.server.security.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private final TokenService tokenService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsersRepository usersRepository;

    @Autowired
    public AuthService(TokenService tokenService, EmailService emailService, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, UsersRepository usersRepository) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usersRepository = usersRepository;
    }

    public String signIn(SignInRequest signInRequest) {
        String token;

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.usernameOrEmailAddress(), signInRequest.password())
            );

            token = tokenService.generateToken(authentication);

            CustomUser userDetails = (CustomUser) authentication.getPrincipal();
            if (!userDetails.isAccountConfirmed()) {
                return "Email address is not confirmed";
            }

            if (userDetails.isAccountLocked()) {
                return "Account is Locked";
            }

        } catch (BadCredentialsException ex) {
            return "Bad Credentials";
        }
        return token;
    }

    public String signUp(SignUpRequest signUpRequest) throws IOException {
        int userByUsername = usersRepository.findByUsername(signUpRequest.username());
        int userByEmailAddress = usersRepository.findByEmailAddress(signUpRequest.emailAddress());

        if (userByUsername >= 1) {
            return "Username already exists";
        }

        if (userByEmailAddress >= 1) {
            return "Email address already exists";
        }

        String generatedToken = tokenService.generateEmailToken(signUpRequest.emailAddress(), "CONFIRM_EMAIL");

        if (createUserAndRelatedData(signUpRequest, generatedToken)) {
            emailService.confirm(signUpRequest, generatedToken);
            return "Account successfully created";
        } else {
            return "Something went wrong";
        }
    }

    public String confirmEmailAddress(Authentication authentication, TokenRequest tokenRequest) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        Optional<User> userBy = usersRepository.findByUsernameOrEmailAddress(customUser.getEmailAddress());
        if (!userBy.isPresent()) {
            return "User with this email address can not be found";
        }

        User user = userBy.get();
        if (user.isAccountConfirmed()) {
            return "Your account already been confirmed";
        }
        return usersRepository.confirmEmailAddress(customUser.getEmailAddress(), tokenRequest.token()) == 1 ? "Account confirmed" : "Something went wrong";
    }

    public String newPassword(Authentication authentication, NewPassword newPassword) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        Optional<User> userBy = usersRepository.findByUsernameOrEmailAddress(customUser.getEmailAddress());
        if (!userBy.isPresent()) {
            return "User with this email address can not be found";
        }

        User user = userBy.get();
        if (user.getPasswordChangeTimer() != null && Duration.between(user.getPasswordChangeTimer(), LocalDateTime.now()).compareTo(Duration.ofDays(1)) <= 0 && user.getPasswordChangeTimer().isAfter(LocalDateTime.now())) {
            return "Time till next allowed attempt: " +
                    Duration.between(LocalDateTime.now(), user.getPasswordChangeTimer()).toHours()
                    + " hr and " +
                    (Duration.between(LocalDateTime.now(), user.getPasswordChangeTimer()).toMinutes() % 60)
                    + " min and " +
                    (Duration.between(LocalDateTime.now(), user.getPasswordChangeTimer()).toSeconds() % 60) + " seconds";
        } else {
            usersRepository.setPasswordChangeTimer(user.getEmailAddress());
        }

        int newUserPassword = usersRepository.newPassword(customUser.getEmailAddress(), bCryptPasswordEncoder.encode(newPassword.password()));
        return newUserPassword == 1 ? "Password is updated" : "Something went wrong";
    }

    private boolean createUserAndRelatedData(SignUpRequest signUpRequest, String generatedToken) {
        if (usersRepository.insertUser(
                signUpRequest.fullName(),
                signUpRequest.username(),
                signUpRequest.emailAddress(),
                bCryptPasswordEncoder.encode(signUpRequest.password()),
                LocalDate.parse(signUpRequest.birthdate()),
                signUpRequest.country(),
                generatedToken) == 1) {

            if (usersRepository.insertUserRole(signUpRequest.username()) == 1 &&
                    usersRepository.insertUserStatistics(signUpRequest.username()) == 1 &&
                    usersRepository.insertUserWeaponStatistics(signUpRequest.username()) == 1 &&
                    usersRepository.insertUserMissions(signUpRequest.username()) == 1) {
                return true;
            } else {
                cleanUpFailedRegistration(signUpRequest.username(), signUpRequest.emailAddress());
                return false;
            }
        } else {
            return false;
        }
    }

    private void cleanUpFailedRegistration(String username, String emailAddress) {
        usersRepository.deleteUser(username, emailAddress);
        usersRepository.deleteRole(username);
        usersRepository.deleteUserStatistics(username);
        usersRepository.deleteUserWeaponStatistics(username);
        usersRepository.deleteUserMissions(username);
    }
}