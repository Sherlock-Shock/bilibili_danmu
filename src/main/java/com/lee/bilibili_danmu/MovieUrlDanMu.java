package com.lee.bilibili_danmu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MovieUrlDanMu extends AbstractDanMu implements DanMu{


    MovieUrlDanMu(String filePath, String movieUrl) {
        super(filePath, movieUrl);
    }

    @Override
    public void getDanMu() {
        super.getDanMuAbs();
    }

    @Override
    public String getBv(String movieUrl) {
        String BV = "";
        Document document = null;
        try{
            document = Jsoup.connect(movieUrl)
                    .timeout(10000)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                    .get();
            String str = document.toString();
            String str2 = str.substring(str.indexOf("bvid")+7);
            BV = str2.substring(0,str2.indexOf(",")-1);
        }catch(IOException e){

        }
        System.out.println("BV:"+BV);
        return BV;
    }
}
