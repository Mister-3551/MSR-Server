package eu.msr.server.repository;

import eu.msr.server.entity.Home;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeRepository extends JpaRepository<Home, Long> {

    @Query(value = "SELECT 0 AS id, u.username, s.rank, u.image, '' AS leaderboard_image, '' AS missions_image, '' AS statistics_image, 0 AS total_users, COUNT(m.id) AS missions " +
            "FROM users u " +
            "INNER JOIN statistics s ON s.id_user = u.id " +
            "JOIN missions m " +
            "WHERE u.username = :username", nativeQuery = true)
    Home userHome(@Param("username") String username);

    @Query(value = "SELECT 0 AS id, '' AS username, 0 AS rank, u.image, '' AS leaderboard_image, '' AS missions_image, '' AS statistics_image, COUNT(u.id) AS total_users, (SELECT COUNT(id) FROM missions) AS missions " +
            "FROM users u " +
            "INNER JOIN statistics s ON s.id_user = u.id", nativeQuery = true)
    Home adminHome(@Param("username") String username);
}