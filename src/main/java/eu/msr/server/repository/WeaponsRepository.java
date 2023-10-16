package eu.msr.server.repository;

import eu.msr.server.entity.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface WeaponsRepository extends JpaRepository<Weapon, Long> {

    @Query(value = "SELECT w.id, w.name, w.image, w.price, CASE WHEN user_weapons.owned IS NULL THEN 0 ELSE user_weapons.owned END AS owned, " +
            "       w.shot_power, w.shots_per_second, " +
            "       CASE WHEN user_weapons.shot_bullets IS NULL THEN 0 ELSE user_weapons.shot_bullets END AS shot_bullets, " +
            "       CASE WHEN user_weapons.total_kills IS NULL THEN 0 ELSE user_weapons.total_kills END AS total_kills, " +
            "       CASE WHEN user_weapons.enemy_kills IS NULL THEN 0 ELSE user_weapons.enemy_kills END AS enemy_kills, " +
            "       CASE WHEN user_weapons.hostage_kills IS NULL THEN 0 ELSE user_weapons.hostage_kills END AS hostage_kills, " +
            "       CASE WHEN user_weapons.vip_kills IS NULL THEN 0 ELSE user_weapons.vip_kills END AS vip_kills " +
            "FROM weapons w " +
            "LEFT JOIN ( " +
            "    SELECT DISTINCT ws.id_weapon AS id_weapon, " +
            "       true AS owned, " +
            "       ws.shot_bullets AS shot_bullets, " +
            "       ws.total_kills AS total_kills, " +
            "       ws.enemy_kills AS enemy_kills, " +
            "       ws.hostage_kills AS hostage_kills, " +
            "       ws.vip_kills AS vip_kills " +
            "    FROM weapons_statistics ws " +
            "    JOIN users u ON u.id = ws.id_user " +
            "    WHERE u.username = :username" +
            ") AS user_weapons ON user_weapons.id_weapon = w.id", nativeQuery = true)
    ArrayList<Weapon> weapons(@Param("username") String username);
}