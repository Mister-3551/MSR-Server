package eu.msr.server.repository;

import eu.msr.server.entity.ChartStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AdminRepository extends JpaRepository<ChartStatistics, Long> {

    @Query(value = "SELECT CONCAT('W', ROW_NUMBER() OVER (ORDER BY rounded_data.id)) AS id, " +
            "       rounded_data.name AS string, " +
            "       CASE " +
            "           WHEN rounded_data.rounded_kills = (MAX(rounded_data.rounded_kills) OVER ()) THEN rounded_data.rounded_kills - (SUM(rounded_data.rounded_kills) OVER () - 100) " +
            "           ELSE rounded_data.rounded_kills " +
            "       END AS number " +
            "FROM ( " +
            "         SELECT w.id, w.name, ROUND(IFNULL((ws.total_kills / _total_kills) * 100, 0), 2) AS rounded_kills " +
            "         FROM weapons w\n" +
            "                  LEFT JOIN weapons_statistics ws ON ws.id_weapon = w.id " +
            "                  CROSS JOIN (SELECT IFNULL(SUM(ws.total_kills), 0) AS _total_kills FROM weapons_statistics ws) AS total " +
            "         GROUP BY w.id, w.name " +
            "     ) AS rounded_data " +
            "ORDER BY rounded_data.id ", nativeQuery = true)
    ArrayList<ChartStatistics> weaponsStatistics();

    @Query(value = "WITH categories AS ( " +
            "    SELECT '< 1h' AS time_category, 1 AS sort_order " +
            "    UNION SELECT '< 10h', 2 " +
            "    UNION SELECT '< 100h', 3 " +
            "    UNION SELECT '< 1000h', 4 " +
            "    UNION SELECT '> 1000h', 5 " +
            ") " +
            "SELECT CONCAT('P', ROW_NUMBER() OVER (ORDER BY c.sort_order ASC)) AS id, c.time_category AS string, COALESCE(COUNT(s.id), 0) AS number " +
            "FROM categories c " +
            "LEFT JOIN statistics s ON c.time_category =  " +
            "    (CASE " +
            "        WHEN s.play_time < 3600 THEN '< 1h' " +
            "        WHEN s.play_time < 36000 THEN '< 10h' " +
            "        WHEN s.play_time < 360000 THEN '< 100h' " +
            "        WHEN s.play_time < 3600000 THEN '< 1000h' " +
            "        ELSE '> 1000h' " +
            "    END) " +
            "GROUP BY c.time_category " +
            "ORDER BY id ASC ", nativeQuery = true)
    ArrayList<ChartStatistics> playTimeStatistics();

    @Query(value = "SELECT CONCAT('M', ROW_NUMBER() OVER ()) AS id," +
            "    string.percent_range AS string," +
            "    COUNT(user_completion_percentage.completed_percentage) AS number " +
            "FROM (" +
            "    SELECT '< 10%' AS percent_range" +
            "    UNION SELECT '< 20%'" +
            "    UNION SELECT '< 30%'" +
            "    UNION SELECT '< 40%'" +
            "    UNION SELECT '< 50%'" +
            "    UNION SELECT '< 60%'" +
            "    UNION SELECT '< 70%'" +
            "    UNION SELECT '< 80%'" +
            "    UNION SELECT '< 90%'" +
            "    UNION SELECT '< 100%'" +
            ") AS string " +
            "LEFT JOIN (" +
            "    SELECT" +
            "        u.id AS user_id," +
            "        (" +
            "            SELECT COUNT(*)" +
            "            FROM missions_completed mc" +
            "            WHERE mc.id_user = u.id" +
            "        ) / (" +
            "            SELECT COUNT(*)" +
            "            FROM missions" +
            "        ) * 100 AS completed_percentage" +
            "    FROM users u" +
            ") AS user_completion_percentage " +
            "ON string.percent_range = (CASE" +
            "    WHEN user_completion_percentage.completed_percentage < 10 THEN '< 10%'" +
            "    WHEN user_completion_percentage.completed_percentage < 20 THEN '< 20%'" +
            "    WHEN user_completion_percentage.completed_percentage < 30 THEN '< 30%'" +
            "    WHEN user_completion_percentage.completed_percentage < 40 THEN '< 40%'" +
            "    WHEN user_completion_percentage.completed_percentage < 50 THEN '< 50%'" +
            "    WHEN user_completion_percentage.completed_percentage < 60 THEN '< 60%'" +
            "    WHEN user_completion_percentage.completed_percentage < 70 THEN '< 70%'" +
            "    WHEN user_completion_percentage.completed_percentage < 80 THEN '< 80%'" +
            "    WHEN user_completion_percentage.completed_percentage < 90 THEN '< 90%'" +
            "    WHEN user_completion_percentage.completed_percentage <= 100 THEN '< 100%'" +
            "    ELSE NULL " +
            "END)" +
            "GROUP BY string.percent_range " +
            "ORDER BY " +
            "    CASE" +
            "        WHEN string.percent_range = '< 10%' THEN 1" +
            "        WHEN string.percent_range = '< 20%' THEN 2" +
            "        WHEN string.percent_range = '< 30%' THEN 3" +
            "        WHEN string.percent_range = '< 40%' THEN 4" +
            "        WHEN string.percent_range = '< 50%' THEN 5" +
            "        WHEN string.percent_range = '< 60%' THEN 6" +
            "        WHEN string.percent_range = '< 70%' THEN 7" +
            "        WHEN string.percent_range = '< 80%' THEN 8" +
            "        WHEN string.percent_range = '< 90%' THEN 9" +
            "        WHEN string.percent_range = '< 100%' THEN 10" +
            "    END", nativeQuery = true)
    ArrayList<ChartStatistics> successfulMissions();
}