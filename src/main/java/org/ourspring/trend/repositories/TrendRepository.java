package org.ourspring.trend.repositories;

import org.ourspring.trend.entities.Trend;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TrendRepository extends ListCrudRepository<Trend, Long> {

    @Query("SELECT * FROM TREND WHERE category=:category ORDER BY createdAt DESC LIMIT 1")
    Optional<Trend> getLatest(@Param("category") String category);

    @Query("SELECT * FROM TREND WHERE category=:category AND createdAt BETWEEN :start AND :end ORDER BY createdAt DESC")
    List<Trend> getPeriodTrend(@Param("category") String category, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
