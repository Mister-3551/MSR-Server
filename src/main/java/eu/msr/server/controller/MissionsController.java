package eu.msr.server.controller;

import eu.msr.server.entity.Mission;
import eu.msr.server.entity.MissionStatistics;
import eu.msr.server.record.AddMission;
import eu.msr.server.record.MissionName;
import eu.msr.server.service.MissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    //TODO make a function to check valid parameters
    @PostMapping("/a/add-mission")
    public boolean add(Authentication authentication,
                       @RequestParam("name") String name,
                       @RequestParam("description") String description,
                       @RequestParam("image") MultipartFile image,
                       @RequestParam("map") MultipartFile map,
                       @RequestParam("price") float price,
                       @RequestParam("bestTime") String bestTime,
                       @RequestParam("deadline") String deadline) throws IOException {
        return missionsService.add(new AddMission(name, description, image, map, price, bestTime, deadline));
    }
}