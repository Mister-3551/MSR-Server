package eu.msr.server.repository;

import eu.msr.server.entity.MissionStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MissionStatisticsRepository extends JpaRepository<MissionStatistics, Long> {

    @Query(value = "SELECT ms.id, ms.status, ms.lost_lives, ms.eliminated_enemies, ms.used_time, ms.score, ms.created_at " +
            "FROM missions_statistics ms " +
            "LEFT JOIN missions m ON m.id = ms.id_mission " +
            "JOIN users u ON u.id = ms.id_user " +
            "WHERE u.username = :username " +
            "AND REPLACE(m.name, ' ', '_') = :missionName", nativeQuery = true)
    ArrayList<MissionStatistics> getMissionStatistics(@Param("username") String username,
                                                      @Param("missionName") String missionName);
}