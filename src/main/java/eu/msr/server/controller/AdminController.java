package eu.msr.server.controller;

import eu.msr.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/a/statistics")
    public Map<String, Object> weaponStatistics(Authentication authentication) {
        return adminService.weaponStatistics();
    }
}