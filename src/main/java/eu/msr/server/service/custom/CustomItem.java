package eu.msr.server.service.custom;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CustomItem {

    private Long id;
    private String title;
    private String text;
    private String image;
    private Set<String> images;
    private String createdAt;

    public CustomItem(Long id, String title, String text, String image, Set<String> images, String createdAt) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.image = image;
        this.images = images;
        this.createdAt = createdAt;
    }
}