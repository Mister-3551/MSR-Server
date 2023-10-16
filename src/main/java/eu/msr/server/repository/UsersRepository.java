package eu.msr.server.repository;

import eu.msr.server.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT " +
            "u.id, " +
            "u.full_name, " +
            "u.username, " +
            "u.email_address, " +
            "u.password, " +
            "GROUP_CONCAT(a.authority) AS authorities, " +
            "u.birth_date, " +
            "u.country, " +
            "CASE WHEN u.account_confirmed = '1' THEN true ELSE false END AS account_confirmed, " +
            "CASE WHEN u.account_locked = '1' THEN true ELSE false END AS account_locked, " +
            "u.unlock_date, " +
            "u.password_reset_timer, " +
            "u.password_change_timer, " +
            "u.reports_number " +
            "FROM users u " +
            "INNER JOIN users_authorities ua ON ua.id_user = u.id " +
            "INNER JOIN authorities a ON a.id = ua.id_authority " +
            "WHERE u.username = :usernameOrEmailAddress OR u.email_address = :usernameOrEmailAddress", nativeQuery = true)
    Optional<User> findByUsernameOrEmailAddress(@Param("usernameOrEmailAddress") String usernameOrEmailAddress);

    @Query(value = "SELECT COUNT(u.id) AS user " +
            "FROM users u " +
            "WHERE u.username = :username", nativeQuery = true)
    int findByUsername(@Param("username") String username);

    @Query(value = "SELECT COUNT(u.id) AS user " +
            "FROM users u " +
            "WHERE u.email_address = :emailAddress", nativeQuery = true)
    int findByEmailAddress(@Param("emailAddress") String emailAddress);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (full_name, username, email_address, password, birth_date, country, account_confirmed) VALUES (:fullName, :username, :emailAddress, :password, :birthDate, :country, :token) ", nativeQuery = true)
    int insertUser(@Param("fullName") String fullName,
                   @Param("username") String username,
                   @Param("emailAddress") String emailAddress,
                   @Param("password") String password,
                   @Param("birthDate") LocalDate birthDate,
                   @Param("country") String country,
                   @Param("token") String token);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users_authorities (id_user, id_authority) " +
            "SELECT u.id, 1 " +
            "FROM users u " +
            "WHERE u.username = :username", nativeQuery = true)
    int insertUserRole(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO statistics (id_user, `rank`, money, current_xp, next_xp, play_time) " +
            "SELECT u.id, '1', '0', '0', '100', '0' " +
            "FROM users u " +
            "WHERE u.username = :username", nativeQuery = true)
    int insertUserStatistics(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO weapons_statistics (id_user, id_weapon, shot_bullets, total_kills, enemy_kills, hostage_kills, vip_kills) " +
            "SELECT u.id, '1', '0', '0', '0', '0', '0' " +
            "FROM users u WHERE u.username = :username", nativeQuery = true)
    int insertUserWeaponStatistics(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO missions_completed (id_user, id_mission, completed) " +
            "SELECT u.id, '1', '2' " +
            "FROM users u " +
            "WHERE u.username = :username", nativeQuery = true)
    int insertUserMissions(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE " +
            "FROM users " +
            "WHERE username = :username " +
            "AND email_address = :emailAddress", nativeQuery = true)
    int deleteUser(@Param("username") String username,
                   @Param("emailAddress") String emailAddress);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users_authorities " +
            "WHERE id_user IN (SELECT u.id " +
            "                  FROM users u " +
            "                  WHERE u.username = :username)", nativeQuery = true)
    void deleteRole(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM statistics " +
            "WHERE id_user IN (SELECT u.id " +
            "                  FROM users u " +
            "                  WHERE u.username = :username)", nativeQuery = true)
    void deleteUserStatistics(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM weapons_statistics " +
            "WHERE id_user IN (SELECT u.id " +
            "                  FROM users u " +
            "                  WHERE u.username = :username)", nativeQuery = true)
    void deleteUserWeaponStatistics(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE " +
            "FROM missions_completed " +
            "WHERE id_user IN (SELECT u.id " +
            "                  FROM users u " +
            "                  WHERE u.username = :username)", nativeQuery = true)
    void deleteUserMissions(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u " +
            "SET u.account_confirmed = CASE WHEN u.account_confirmed = :token THEN '1' ELSE u.account_confirmed END " +
            "WHERE u.email_address = :emailAddress", nativeQuery = true)
    int confirmEmailAddress(@Param("emailAddress") String emailAddress,
                            @Param("token") String token);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u " +
            "SET u.password = :password " +
            "WHERE u.email_address = :emailAddress", nativeQuery = true)
    int newPassword(@Param("emailAddress") String emailAddress,
                    @Param("password") String password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u " +
            "SET u.password_reset_timer = DATE_ADD(NOW(), INTERVAL 30 MINUTE) " +
            "WHERE u.email_address = :emailAddress", nativeQuery = true)
    void setPasswordResetTimer(@Param("emailAddress") String emailAddress);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u " +
            "SET u.password_change_timer = DATE_ADD(NOW(), INTERVAL 1 DAY) " +
            "WHERE u.email_address = :emailAddress", nativeQuery = true)
    void setPasswordChangeTimer(@Param("emailAddress") String emailAddress);
}