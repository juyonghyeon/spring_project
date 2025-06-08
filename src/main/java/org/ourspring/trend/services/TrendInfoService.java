package org.ourspring.trend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.ourspring.admin.trend.controllers.TrendUrl;
import org.ourspring.global.search.CommonSearch;
import org.ourspring.trend.entities.Trend;
import org.ourspring.trend.exceptions.TrendNotFoundException;
import org.ourspring.trend.repositories.TrendRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Lazy
@Service
@RequiredArgsConstructor
public class TrendInfoService {

    private final TrendRepository repository;

    private final TrendCrawlingService crawlingService;

    private final ObjectMapper om;

    /**
     * 최근 트렌드 1개 조회
     *
     * @param category
     * @return
     */
    public Trend getLatest(String category) {
        Trend item = repository.getLatest(category).orElseThrow(TrendNotFoundException::new);

        TrendUrl search = new TrendUrl();
        return item;
    }


    /**
     * 특정 날짜의 트렌드 데이터 1개
     * @param date
     * @return
     */
    public Trend get(String category, LocalDate date) {
        return repository.get(category, date.atStartOfDay(), LocalDateTime.of(date, LocalTime.of(23, 59, 59))).orElse(null);
    }

    /**
     * 특정 날짜 범위의 트렌드 데이터 조회
     *
     * @return
     */
    public List<Trend> getList(String category, CommonSearch search) {
//        LocalDateTime start = search.getSDate().atStartOfDay();
//        LocalDateTime end = search.getEDate().atTime(23, 59, 59);
//        List<Trend> data = repository.getPeriodTrend(category, start, end);

        LocalDate start = Objects.requireNonNullElse(search.getSDate(), LocalDate.now());
        LocalDate end = Objects.requireNonNullElse(search.getEDate(), LocalDate.now());
        List<Trend> data = repository.getPeriodTrend(category, start.atStartOfDay(), LocalDateTime.of(end, LocalTime.of(23, 59, 59)));

        return data;
    }



    // 트렌드 데이터 조회
    public Map<String, Object> getStat(String url) {

        crawlingService.process(url); // 데이터 한번 수집

        Map<String, Object> statData = new HashMap<>(); // 통계 데이터

        String category = url.contains("news.naver.com") ? "NEWS" : "ETC_" + Objects.hash(url);
        LocalDate today = LocalDate.now();
        LocalDate last7Date = today.minusDays(6L); // 일주일 전
        LocalDate last30Date = today.minusDays(29L); // 한달 전

        Trend now = get(category, today); // 조회 시점 데이터 수집
        statData.put("now", now);


        CommonSearch search = new CommonSearch();
        search.setEDate(today);

        // 일주일 트렌드
        search.setSDate(last7Date);
        List<Trend> last7Days = getList(category, search);
        Map<LocalDate, Map<String, Integer>> last7DayData = preprocessing(last7Days);
        statData.put("oneWeek", last7DayData);
        statData.put("oneWeekWordCloud", getWordCloudPath(last7DayData));

        // 한달 트렌드
        search.setSDate(last30Date);
        List<Trend> last30Days = getList(category, search);
        Map<LocalDate, Map<String, Integer>> last30DayData = preprocessing(last30Days);
        statData.put("oneMonth", last30DayData);
        statData.put("oneMonthWordCloud", getWordCloudPath(last30DayData));


        // JSON 변환 데이터
        try {
            statData.put("json", om.writeValueAsString(statData));
        } catch (JsonProcessingException e) {}

        return statData;
    }

    private  Map<LocalDate, Map<String, Integer>> preprocessing(List<Trend> trends) {
        if (trends == null) return null;

        Map<LocalDate, Map<String, Integer>> itemsTotal = new HashMap<>();
        Map<LocalDate, Map<String, Integer>> itemsAvg = new TreeMap<>();
        Map<LocalDate, Map<String, Integer>> itemsCount = new HashMap<>();

        for (Trend trend : trends) {
            LocalDate date = trend.getCreatedAt().toLocalDate();
            Map<String, Integer> total = itemsTotal.getOrDefault(date, new HashMap<>());
            Map<String, Integer> avg = itemsAvg.getOrDefault(date, new HashMap<>());
            Map<String, Integer> count = itemsCount.getOrDefault(date, new HashMap<>());

            try {
                Map<String, Integer> keywords = om.readValue(trend.getKeywords(), new TypeReference<>() {});
                keywords.forEach((key, value) -> {
                    int t = total.getOrDefault(key, 0) + value;
                    int c = count.getOrDefault(key, 0) + 1;
                    total.put(key, t); // 합계
                    count.put(key, c); // 일별 통계 카운트
                    avg.put(key, (int)Math.round(t / (double)c)); // 합계
                });
            } catch (JsonProcessingException e) {}

            itemsTotal.put(date, total);
            itemsCount.put(date, count);
            itemsAvg.put(date, avg);
        }

        return itemsAvg;
    }


    // 워드 클라우드 이미지 경로
    public String getWordCloudPath(Map<LocalDate, Map<String, Integer>> data) {
        Map<String, Integer> items = new HashMap<>();
        data.values().stream().forEach(m -> {
            m.forEach((k, v) -> {

                int cnt = items.getOrDefault(k, 0) + v;
                items.put(k, cnt);
            });
        });

        try {
            String json = om.writeValueAsString(items);

            return crawlingService.createWordCloud(json);

        } catch (JsonProcessingException e) {}

        return null;
    }

}