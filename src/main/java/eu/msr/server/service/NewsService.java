package eu.msr.server.service;

import eu.msr.server.entity.News;
import eu.msr.server.record.NewsRequest;
import eu.msr.server.repository.NewsRepository;
import eu.msr.server.service.custom.CustomItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public ArrayList<News> getNews() {
        return newsRepository.getNews();
    }

    public ArrayList<CustomItem> getItem(NewsRequest newsRequest) {
        Optional<News> itemById = newsRepository.getItem(newsRequest.id());

        if (!itemById.isPresent()) {
            return null;
        }
        News item = itemById.get();
        Set<String> itemImages = new HashSet<>();

        if (item.getImages() != null) {
            itemImages.addAll(Arrays.asList(item.getImages().split(",")));
        }

        ArrayList<CustomItem> customItems = new ArrayList<>();
        customItems.add(new CustomItem(
                item.getId(),
                item.getTitle(),
                item.getText(),
                item.getImage(),
                itemImages,
                item.getCreatedAt()
        ));
        return customItems;
    }

    public ArrayList<News> getSuggestions(NewsRequest newsRequest) {
        return newsRepository.getSuggestions(newsRequest.id());
    }
}