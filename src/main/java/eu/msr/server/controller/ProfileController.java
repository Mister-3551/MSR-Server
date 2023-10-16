package eu.msr.server.controller;

import eu.msr.server.entity.Account;
import eu.msr.server.entity.Follower;
import eu.msr.server.entity.Profile;
import eu.msr.server.entity.Weapon;
import eu.msr.server.record.ProfileRequest;
import eu.msr.server.record.UsernamesRequest;
import eu.msr.server.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/u/profile")
    public Profile profile(Authentication authentication, @RequestBody UsernamesRequest usernamesRequest) {
        return profileService.profile(authentication, usernamesRequest);
    }

    @PostMapping("/u/profile/follow")
    public boolean follow(Authentication authentication, @RequestBody ProfileRequest profileRequest) {
        return profileService.follow(authentication, profileRequest);
    }

    @PostMapping("/u/profile/weapons")
    public ArrayList<Weapon> weapons(Authentication authentication, @RequestBody ProfileRequest profileRequest) {
        return profileService.weapons(authentication, profileRequest);
    }

    @PostMapping("/u/profile/followers")
    public ArrayList<Follower> followers(Authentication authentication, @RequestBody ProfileRequest profileRequest) {
        return profileService.followers(authentication, profileRequest);
    }

    @PostMapping("/u/profile/following")
    public ArrayList<Follower> following(Authentication authentication, @RequestBody ProfileRequest profileRequest) {
        return profileService.following(authentication, profileRequest);
    }

    @PostMapping("/u/profile/username-checker")
    public boolean usernameChecker(Authentication authentication, @RequestBody ProfileRequest profileRequest) {
        return profileService.usernameChecker(authentication, profileRequest);
    }

    @PostMapping("/u/search")
    public ArrayList<Follower> search(Authentication authentication, @RequestBody ProfileRequest profileRequest) {
        return profileService.search(authentication, profileRequest);
    }

    @PostMapping("/u/account")
    public Account account(Authentication authentication) {
        return profileService.account(authentication);
    }
}