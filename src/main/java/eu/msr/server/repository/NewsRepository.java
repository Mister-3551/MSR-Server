package eu.msr.server.repository;

import eu.msr.server.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = "SELECT n.id, " +
            "n.title, " +
            "LEFT(n.text, 50) AS text, " +
            "n.image, " +
            "NULL AS images, " +
            "DATE_FORMAT(n.created_at, '%b %d, %Y') AS created_at " +
            "FROM news n " +
            "ORDER BY n.created_at DESC", nativeQuery = true)
    ArrayList<News> getNews();

    @Query(value = "SELECT n.id, " +
            "       n.title, " +
            "       n.text, " +
            "       n.image, " +
            "       GROUP_CONCAT(ni.image) AS images,  " +
            "       DATE_FORMAT(n.created_at, '%b %d, %Y') AS created_at " +
            "FROM news n " +
            "LEFT JOIN news_images ni ON ni.id_news = n.id " +
            "WHERE n.id = :itemId", nativeQuery = true)
    Optional<News> getItem(@Param("itemId") Long itemId);

    @Query(value = "SELECT n.id, " +
            "n.title, " +
            "n.text, " +
            "n.image, " +
            "NULL AS images, " +
            "DATE_FORMAT(n.created_at, '%b %d, %Y') AS created_at " +
            "FROM news n " +
            "WHERE n.id != :itemId AND n.id >= (SELECT FLOOR(MAX(n1.id) * RAND()) FROM news n1)" +
            "ORDER BY n.id " +
            "LIMIT 2", nativeQuery = true)
    ArrayList<News> getSuggestions(@Param("itemId") Long itemId);
}