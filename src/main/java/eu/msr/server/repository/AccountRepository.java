package eu.msr.server.repository;

import eu.msr.server.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT u.id, u.full_name, u.username, u.email_address " +
            "FROM users u " +
            "WHERE u.username =:username", nativeQuery = true)
    Account account(@Param("username") String username);
}