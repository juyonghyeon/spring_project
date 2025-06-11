package org.ourspring.trend.services;

import org.junit.jupiter.api.Test;
import org.ourspring.global.search.CommonSearch;
import org.ourspring.trend.entities.Trend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CreateWordCloudTest {

    @Autowired
    TrendInfoService infoService;

    @Test
    void test(){
        LocalDate today = LocalDate.now();
        LocalDate last7Date = today.minusDays(6L);

        CommonSearch search = new CommonSearch();

        search.setEDate(today);
        search.setSDate(last7Date);

        List<Trend> a7 = infoService.getList("NEWS", search);
        Map<LocalDate, Map<String, Integer>> last7DayData = infoService.preprocessing(a7);
        //System.out.println(last7DayData);
        infoService.getWordCloudPath(last7DayData);
    }
}
