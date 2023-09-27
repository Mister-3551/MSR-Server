package eu.msr.server.repository;

import eu.msr.server.entity.Contact;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO contact (full_name, email_address, subject, message) " +
            "VALUES (:fullName, :emailAddress, :subject, :message)", nativeQuery = true)
    int insertMessage(String fullName, String emailAddress, String subject, String message);

    @Query(value = "SELECT COUNT(c.id) FROM contact c " +
            "WHERE c.email_address = :emailAddress " +
            "AND c.subject = :subject " +
            "AND c.message = :message", nativeQuery = true)
    int checkDuplicate(String emailAddress, String subject, String message);
}