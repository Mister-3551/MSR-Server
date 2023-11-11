package eu.msr.server.service;

import eu.msr.server.entity.Home;
import eu.msr.server.repository.HomeRepository;
import eu.msr.server.security.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

    private final HomeRepository homeRepository;

    @Autowired
    public HomeService(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    public Home userHome(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Home user = homeRepository.userHome(customUser.getUsername());
        user.setMissionsImage("missions.png");
        user.setLeaderboardImage("leaderboard.png");
        return user;
    }

    public Home adminHome(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Home admin = homeRepository.userHome(customUser.getUsername());
        admin.setStatisticsImage("statistics.png");
        admin.setMissionsImage("missions.png");
        return admin;
    }
}