package eu.msr.server.service;

import eu.msr.server.entity.Account;
import eu.msr.server.entity.Follower;
import eu.msr.server.entity.Profile;
import eu.msr.server.entity.Weapon;
import eu.msr.server.record.ProfileRequest;
import eu.msr.server.record.UsernamesRequest;
import eu.msr.server.repository.AccountRepository;
import eu.msr.server.repository.FollowersRepository;
import eu.msr.server.repository.ProfileRepository;
import eu.msr.server.repository.WeaponsRepository;
import eu.msr.server.security.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final WeaponsRepository weaponsRepository;
    private final FollowersRepository followersRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, WeaponsRepository weaponsRepository, FollowersRepository followersRepository, AccountRepository accountRepository) {
        this.profileRepository = profileRepository;
        this.weaponsRepository = weaponsRepository;
        this.followersRepository = followersRepository;
        this.accountRepository = accountRepository;
    }

    public Profile profile(Authentication authentication, UsernamesRequest usernamesRequest) {
        return profileRepository.profile(usernamesRequest.username(), usernamesRequest.username1());
    }

    public boolean follow(Authentication authentication, ProfileRequest profileRequest) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        if (profileRepository.checkFollow(customUser.getUsername(), profileRequest.username()) == 1) {
            if (profileRepository.removeFollow(customUser.getUsername(), profileRequest.username()) == 1) {
                return false;
            }
        }
        return profileRepository.follow(customUser.getUsername(), profileRequest.username()) == 1;
    }

    public ArrayList<Weapon> weapons(Authentication authentication, ProfileRequest profileRequest) {
        return weaponsRepository.weapons(profileRequest.username());
    }

    public ArrayList<Follower> followers(Authentication authentication, ProfileRequest profileRequest) {
        return followersRepository.followers(profileRequest.username());
    }

    public ArrayList<Follower> following(Authentication authentication, ProfileRequest profileRequest) {
        return followersRepository.following(profileRequest.username());
    }

    public boolean usernameChecker(Authentication authentication, ProfileRequest profileRequest) {
        return profileRepository.usernameChecker(profileRequest.username()) == 1;
    }

    public ArrayList<Follower> search(Authentication authentication, ProfileRequest profileRequest) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return followersRepository.search(profileRequest.username());
    }

    public Account account(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return accountRepository.account(customUser.getUsername());
    }
}