package org.ourspring.trend.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NewsTrendServiceTest {

    @Autowired
    private TrendCrawlingService service;

    @Test
    void test() {
        //NewsTrend data = service.process();
        //System.out.println(data);
        //service.process("https://news.naver.com/");
    }
}
