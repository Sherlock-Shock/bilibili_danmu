package com.lee.bilibili_danmu;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class BilibiliDanmuApplication {


    public static void main(String[] args) {
        SpringApplication.run(BilibiliDanmuApplication.class, args);

        String filePath = "d:/pachongtupian/guojierui";
        String Url = "https://www.bilibili.com/video/BV1SA411J7zM?spm_id_from=333.851.b_7265706f7274466972737431.7";

        DanMuFactory danMuFactory = new DanMuFactory();
//        danMuFactory.createBvUrlDanMu(filePath,Url).getDanMu();
//        danMuFactory.createBvDanMu(filePath,"BV1jh411o7dZ").getDanMu();
//        danMuFactory.createMovieUrlDanMu(filePath,"https://www.bilibili.com/bangumi/play/ss33906").getDanMu();
        danMuFactory.createPersonAllDanMu(filePath,"https://space.bilibili.com/176037767?from=search&seid=9884943658557322459").getDanMu();


    }

}
