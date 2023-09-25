package eu.msr.server.record;

public record SignInRequest(
        String usernameOrEmailAddress,
        String password) {
}