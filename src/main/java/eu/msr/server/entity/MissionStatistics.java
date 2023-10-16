package eu.msr.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class MissionStatistics {

    @Id
    private Long id;
    private int status;
    private int lostLives;
    private int eliminatedEnemies;
    private Time usedTime;
    private int score;
    private String createdAt;
}