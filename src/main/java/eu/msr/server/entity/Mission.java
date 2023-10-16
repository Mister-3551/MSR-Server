package eu.msr.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@Entity
public class Mission {

    @Id
    private Long Id;
    private String name;
    private String image;
    private String map;
    private String description;
    private float price;
    private Time bestTime;
    private Time deadline;
    private int completed;
    private String completedAt;
}