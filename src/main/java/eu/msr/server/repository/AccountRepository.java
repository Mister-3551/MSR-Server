package eu.msr.server.repository;

import eu.msr.server.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT u.id, u.full_name, u.username, u.email_address " +
            "FROM users u " +
            "WHERE u.username =:username", nativeQuery = true)
    Account account(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u " +
            "SET u.full_name = :fullName, " +
            "    u.image = :image " +
            "WHERE u.username = :username", nativeQuery = true)
    int accountUpdate(@Param("username") String username,
                      @Param("image") String image,
                      @Param("fullName") String fullName);
}