package eu.msr.server.controller;

import eu.msr.server.entity.Home;
import eu.msr.server.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @PostMapping("/u/")
    public Home userHome(Authentication authentication) {
        return homeService.userHome(authentication);
    }

    @PostMapping("/a/")
    public Home AdminHome(Authentication authentication) {
        return homeService.adminHome(authentication);
    }
}