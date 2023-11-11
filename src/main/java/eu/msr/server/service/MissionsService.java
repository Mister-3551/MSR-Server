package eu.msr.server.service;


import eu.msr.server.entity.Mission;
import eu.msr.server.entity.MissionStatistics;
import eu.msr.server.record.AddMission;
import eu.msr.server.record.MissionName;
import eu.msr.server.repository.MissionStatisticsRepository;
import eu.msr.server.repository.MissionsRepository;
import eu.msr.server.security.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class MissionsService {

    private final FileService fileService;
    private final MissionsRepository missionsRepository;
    private final MissionStatisticsRepository missionStatisticsRepository;

    @Autowired
    public MissionsService(FileService fileService, MissionsRepository missionsRepository, MissionStatisticsRepository missionStatisticsRepository) {
        this.fileService = fileService;
        this.missionsRepository = missionsRepository;
        this.missionStatisticsRepository = missionStatisticsRepository;
    }

    public ArrayList<Mission> missions(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return missionsRepository.getMissions(customUser.getUsername());
    }

    public ArrayList<MissionStatistics> missionsStatistics(Authentication authentication, MissionName missionName) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return missionStatisticsRepository.getMissionStatistics(customUser.getUsername(), missionName.name());
    }

    public boolean check(MissionName missionName) {
        return missionsRepository.checkMission(missionName.name()) == 1;
    }

    public boolean add(AddMission addMission) throws IOException {
        if (addMission.image() == null || addMission.map() == null) {
            return false;
        }

        String imageName = fileService.storeFile(addMission.name().trim().replace(" ", "_"), addMission.image(), FileService.Types.MISSION);
        if (imageName != null) {
            String mapName = fileService.storeFile(addMission.name().trim().replace(" ", "_"), addMission.map(), FileService.Types.MAP);
            if (mapName != null) {
                return missionsRepository.add(addMission.name(), addMission.description(), imageName, mapName, addMission.price(), addMission.bestTime(), addMission.deadline()) == 1;
            }
        }
        return false;
    }
}