package eu.msr.server.controller;

import eu.msr.server.entity.Mission;
import eu.msr.server.entity.MissionStatistics;
import eu.msr.server.record.MissionName;
import eu.msr.server.service.MissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MissionsController {

    private final MissionsService missionsService;

    @Autowired
    public MissionsController(MissionsService missionsService) {
        this.missionsService = missionsService;
    }

    @PostMapping("/u/missions")
    public ArrayList<Mission> missions(Authentication authentication) {
        return missionsService.missions(authentication);
    }

    @PostMapping("/u/missions/statistics")
    public ArrayList<MissionStatistics> missionsStatistics(Authentication authentication, @RequestBody MissionName missionName) {
        return missionsService.missionsStatistics(authentication, missionName);
    }

    @PostMapping("/u/missions/statistics/check")
    public boolean check(Authentication authentication, @RequestBody MissionName missionName) {
        return missionsService.check(missionName);
    }
}