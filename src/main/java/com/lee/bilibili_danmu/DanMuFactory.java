package com.lee.bilibili_danmu;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


public class DanMuFactory {
    public  DanMu createBvUrlDanMu(String filePath,String BvUrl){
        return new BvUrlDanMu(filePath,BvUrl);
    }

    public DanMu createBvDanMu(String filePath,String BV){
        return new BvUrlDanMu(filePath,BV);
    }
    public DanMu createMovieUrlDanMu(String filePath,String MovieUrl){
        return new MovieUrlDanMu(filePath,MovieUrl);
    }
    public DanMu createPersonAllDanMu(String filePath,String spaceUrl){
        return new PersonAllDanMu(filePath,spaceUrl);
    }
}
