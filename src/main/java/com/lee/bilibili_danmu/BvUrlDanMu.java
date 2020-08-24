package com.lee.bilibili_danmu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

public class BvUrlDanMu extends AbstractDanMu implements DanMu {
    String filePath;
    String Url;

    BvUrlDanMu(String filePath, String UrlOrBv) {
        super(filePath, UrlOrBv);
    }


    @Override
    public String getBv(String UrlOrBv) {
        String BV;
        if (UrlOrBv.contains("?")){
            BV = UrlOrBv.substring(UrlOrBv.lastIndexOf("/")+1,UrlOrBv.lastIndexOf("?"));
        }else if(UrlOrBv.contains("/")){
            System.out.println(UrlOrBv);
            BV = UrlOrBv.substring(UrlOrBv.lastIndexOf("/")+1);
        } else{
            BV = UrlOrBv;
        }
        return BV;
    }


    @Override
    public void getDanMu() {
        super.getDanMuAbs();
    }
}
