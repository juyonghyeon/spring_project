package org.ourspring.admin.trend.controllers;

import lombok.RequiredArgsConstructor;
import org.ourspring.admin.global.controllers.CommonController;
import org.ourspring.trend.entities.Trend;
import org.ourspring.trend.services.TrendInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/trend")
public class TrendController extends CommonController {

    private final TrendInfoService infoService;

    @Override
    @ModelAttribute("mainCode")
    public String mainCode() {
        return "trend";
    }

    @GetMapping({"", "/news"}) // /admin/trend, /admin/trend/news
    public String news(Model model) {
        commonProcess("news", model);

        Trend item = infoService.getLatest("NEWS");
        model.addAttribute("item", item);

        return "admin/trend/news";
    }


    @GetMapping("/etc")
    public String etc(@ModelAttribute TrendUrl search, Model model) {
        commonProcess("etc", model);
        String url = search.getSiteUrl();

        if (StringUtils.hasText(url)) {
            Map<String, Object> data = infoService.getStat(url);
            model.addAllAttributes(data);
        }

        return "admin/trend/etc";
    }





    /**
     * 공통 처리
     *
     * @param code : 서브메뉴 코드
     * @param model
     */
    private void commonProcess(String code, Model model) {
        code = StringUtils.hasText(code) ? code : "news";

        String pageTitle = "";
        List<String> addScript = new ArrayList<>();

        if (code.equals("news")) {
            addScript.add("trend/news");
            pageTitle = "오늘의 뉴스 트렌드";
        } else if (code.equals("etc")) {
            addScript.add("trend/etc");
            pageTitle = "트렌드 분석";
        }

        model.addAttribute("subCode", code);
        model.addAttribute("addScript", addScript);
        model.addAttribute("pageTitle", pageTitle);
    }


}
