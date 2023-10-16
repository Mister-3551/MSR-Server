package eu.msr.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    private Long id;
    private String fullName;
    private String username;
    private String emailAddress;
    private String password;
    private String authorities;
    private LocalDate birthDate;
    private String country;
    private boolean accountConfirmed;
    private boolean accountLocked;
    private LocalDateTime unlockDate;
    private LocalDateTime passwordResetTimer;
    private LocalDateTime passwordChangeTimer;
    private int reportsNumber;
}