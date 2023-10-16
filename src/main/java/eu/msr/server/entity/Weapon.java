package eu.msr.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Weapon {

    @Id
    private Long id;
    private String name;
    private String image;
    private float price;
    private boolean owned;
    private float shotPower;
    private float shotsPerSecond;
    private int shotBullets;
    private int totalKills;
    private int enemyKills;
    private int hostageKills;
    private int vipKills;
}