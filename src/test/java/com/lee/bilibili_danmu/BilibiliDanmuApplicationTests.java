package com.lee.bilibili_danmu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BilibiliDanmuApplicationTests {

    @Test
    void contextLoads() {

        Document document = null;
        for (int i = 1; i < 15; i++) {
            try {
                document = Jsoup.connect("https://api.bilibili.com/x/space/arc/search?mid=176037767&ps=30&tid=0&pn="
                        +i+"&order=pubdate&jsonp=jsonp")
                        .timeout(10000)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                        .get();
                Elements e = document.select("body");
                JSONObject jsonObject = JSONObject.parseObject(e.text().toString());
                JSONArray jsonStr = (JSONArray) jsonObject.getJSONObject("data").getJSONObject("list").get("vlist");
                for (int j = 0; j < jsonStr.size(); j++) {
                    System.out.println(jsonStr.getJSONObject(j).get("title"));
                    System.out.println(jsonStr.getJSONObject(j).get("bvid"));
                }
            }catch (IOException e){

            }
        }


    }

}
