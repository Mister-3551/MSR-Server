package eu.msr.server.repository;

import eu.msr.server.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface FollowersRepository extends JpaRepository<Follower, Long> {

    @Query(value = "SELECT u.id, u.username, s.rank, u.image " +
            "FROM users AS u " +
            "JOIN followers AS f ON u.id = f.follower_id_user " +
            "JOIN statistics AS s ON u.id = s.id_user " +
            "WHERE f.following_id_user IN (SELECT u.id FROM users u WHERE username = :username)", nativeQuery = true)
    ArrayList<Follower> followers(@Param("username") String username);

    @Query(value = "SELECT u.id, u.username, s.rank, u.image " +
            "FROM users AS u " +
            "JOIN followers AS f ON u.id = f.following_id_user " +
            "JOIN statistics AS s ON u.id = s.id_user " +
            "WHERE f.follower_id_user IN (SELECT u.id FROM users u WHERE username = :username)", nativeQuery = true)
    ArrayList<Follower> following(@Param("username") String username);

    @Query(value = "SELECT u.id, u.username, s.rank, u.image " +
            "FROM users AS u " +
            "JOIN statistics AS s ON u.id = s.id_user " +
            "WHERE u.username LIKE %:username%", nativeQuery = true)
    ArrayList<Follower> search(@Param("username") String username);

    @Query(value = "SELECT u.id, u.username, s.rank, u.image " +
            "FROM users u " +
            "JOIN statistics s ON s.id_user = u.id " +
            "JOIN missions_completed mc ON mc.id_user = u.id " +
            "GROUP BY u.id, u.username, s.rank, u.image " +
            "ORDER BY (COUNT(DISTINCT mc.id) / (SELECT COUNT(m.id) FROM missions m)) * 100 DESC, s.rank DESC, s.current_xp DESC " +
            "LIMIT 9", nativeQuery = true)
    ArrayList<Follower> leaderboard();
}