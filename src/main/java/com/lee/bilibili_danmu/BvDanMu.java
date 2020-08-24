package com.lee.bilibili_danmu;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

public class BvDanMu extends AbstractDanMu implements DanMu{

    String filePath;
    String BV;

    BvDanMu(String filePath, String Bv) {
        super(filePath, Bv);
    }


    @Override
    public String getBv(String UrlOrBv) {
        return UrlOrBv;
    }


    @Override
    public void getDanMu() {
        super.getDanMuAbs();
    }
}
