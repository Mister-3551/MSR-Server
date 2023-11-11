package eu.msr.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ChartStatistics {

    @Id
    private Long id;
    private String string;
    private float number;
}