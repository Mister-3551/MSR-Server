package eu.msr.server.controller;

import eu.msr.server.entity.News;
import eu.msr.server.record.NewsRequest;
import eu.msr.server.service.NewsService;
import eu.msr.server.service.custom.CustomItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/get-news")
    public ArrayList<News> getNews() {
        return newsService.getNews();
    }

    @PostMapping("/get-item")
    public ArrayList<CustomItem> getItem(@RequestBody NewsRequest newsRequest) {
        return newsService.getItem(newsRequest);
    }

    @PostMapping("/get-suggestions")
    public ArrayList<News> getSuggestions(@RequestBody NewsRequest newsRequest) {
        return newsService.getSuggestions(newsRequest);
    }
}