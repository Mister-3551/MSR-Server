package eu.msr.server.controller;

import eu.msr.server.record.SignInRequest;
import eu.msr.server.record.SignUpRequest;
import eu.msr.server.security.impl.ValidationException;
import eu.msr.server.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AuthController implements ValidationException {

    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @RequestBody SignUpRequest signUpRequest) throws IOException {
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/sign-out")
    public void signOut() {
    }

    @PostMapping(value = "/token-checker")
    public boolean tokenChecker(Authentication authentication) {
        return authentication.isAuthenticated();
    }
}