package eu.msr.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class News {

    @Id
    private Long id;
    private String title;
    private String text;
    private String image;
    private String images;
    private String createdAt;
}