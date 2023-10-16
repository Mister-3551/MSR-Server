package eu.msr.server.repository;

import eu.msr.server.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MissionsRepository extends JpaRepository<Mission, Long> {

    @Query(value = "SELECT m.id, m.name, m.image, m.map, m.description, m.price, m.best_time, m.deadline, CASE WHEN user_missions.completed IS NULL THEN 0 ELSE user_missions.completed END as completed, user_missions.created_at AS completed_at " +
            "FROM missions m " +
            "LEFT JOIN ( " +
            "    SELECT DISTINCT mc.id_mission AS id_mission, mc.completed AS completed, mc.created_at AS created_at " +
            "    FROM missions_completed mc " +
            "    JOIN users u ON u.id = mc.id_user " +
            "    WHERE u.username = :username " +
            ") AS user_missions ON user_missions.id_mission = m.id", nativeQuery = true)
    ArrayList<Mission> getMissions(@Param("username") String username);

    @Query(value = "SELECT COUNT(m.id) AS id " +
            "FROM missions m " +
            "WHERE REPLACE(m.name, ' ', '_') = :missionName", nativeQuery = true)
    int checkMission( @Param("missionName") String missionName);
}