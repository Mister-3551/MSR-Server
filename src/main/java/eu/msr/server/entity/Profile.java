package eu.msr.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Profile {

    @Id
    private Long id;
    private String username;
    private int rank;
    private String image;
    private int currentXp;
    private int nextXp;
    private int followers;
    private int following;
    private float completed;
    private String playTime;
    private boolean isFollowed;
}