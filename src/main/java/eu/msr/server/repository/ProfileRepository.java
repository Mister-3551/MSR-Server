package eu.msr.server.repository;

import eu.msr.server.entity.Profile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query(value = "SELECT " +
            "    u.id, " +
            "    u.username, " +
            "    s.rank, " +
            "    u.image, " +
            "    s.current_xp, " +
            "    s.next_xp, " +
            "    COUNT(DISTINCT f1.follower_id_user) AS followers, " +
            "    COUNT(DISTINCT f2.following_id_user) AS following, " +
            "    (COUNT(DISTINCT mc.id) / (SELECT COUNT(m.id) FROM missions m)) * 100 AS completed, " +
            "    s.play_time, " +
            "    (SELECT COUNT(s.id) FROM followers s WHERE s.follower_id_user = (SELECT u.id FROM users u WHERE u.username = :username1) AND s.following_id_user = u.id) AS is_followed " +
            "FROM users u " +
            "INNER JOIN statistics s ON s.id_user = u.id " +
            "LEFT JOIN missions_completed mc ON mc.id_user = u.id AND mc.completed = '1' " +
            "LEFT JOIN followers f1 ON f1.following_id_user = u.id " +
            "LEFT JOIN followers f2 ON f2.follower_id_user = u.id " +
            "WHERE u.username = :username", nativeQuery = true)
    Profile profile(@Param("username") String username,
                    @Param("username1") String username1);

    @Query(value = "SELECT COUNT(u.id) AS id " +
            "FROM users u " +
            "WHERE u.username = :username", nativeQuery = true)
    int usernameChecker(@Param("username") String username);

    @Query(value = "SELECT COUNT(f.id) AS id " +
            "FROM followers f " +
            "INNER JOIN users u ON u.id = f.follower_id_user " +
            "INNER JOIN users u1 ON u1.id = f.following_id_user " +
            "WHERE u.username = :username AND u1.username = :username1", nativeQuery = true)
    int checkFollow(@Param("username") String username,
                    @Param("username1") String username1);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO followers (follower_id_user, following_id_user) " +
            "SELECT u1.id, u2.id " +
            "FROM users u1, users u2 " +
            "WHERE u1.username = :username " +
            "AND u2.username = :username1" , nativeQuery = true)
    int follow(@Param("username") String username,
               @Param("username1") String username1);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM followers " +
            "WHERE (follower_id_user, following_id_user) " +
            "IN ( " +
            "  SELECT u1.id, u2.id " +
            "  FROM users u1, users u2 " +
            "  WHERE u1.username = :username " +
            "  AND u2.username = :username1)", nativeQuery = true)
    int removeFollow(@Param("username") String username,
                     @Param("username1") String username1);
}