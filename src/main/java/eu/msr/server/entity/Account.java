package eu.msr.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Account {

    @Id
    private Long id;
    private String fullName;
    private String username;
    private String emailAddress;
}