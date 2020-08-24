package com.lee.bilibili_danmu;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractDanMu {
   private String fileName;
   private String UrlOrBv;
   private String filePath;
   // 正则文件名去除非法字符
   private Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");

    AbstractDanMu(String filePath,String  UrlOrBv){
        this.filePath = filePath;
        this.UrlOrBv = UrlOrBv;
    }

    public abstract String getBv(String UrlOrBv);


    public void getDanMuAbs() {
        String BV = getBv(this.UrlOrBv);
        String cid =getCidFromBv(BV);
        String apiUrl = "https://comment.bilibili.com/"+cid+".xml";
        System.out.println(apiUrl);
        Document document = null;
        try{
            document = Jsoup.connect(apiUrl)
                    .timeout(10000)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                    .get();

            downDanMuXml(this.filePath,getFileName(),document.toString());
        }catch(IOException e){

        }
    }

    public String getCidFromBv(String BV) {
        Document document = null;
        String cid = "";
        String jsonUrl = "https://api.bilibili.com/x/web-interface/view?bvid="+BV;
        try{
            System.out.println(jsonUrl);
            document = Jsoup.connect(jsonUrl)
                    .timeout(10000)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                    .get();
            Elements e = document.select("body");
            JSONObject jsonObject = JSONObject.parseObject(e.text().toString());
            System.out.println(jsonObject);
            cid = jsonObject.getJSONObject("data").get("cid").toString();
            this.fileName=jsonObject.getJSONObject("data").get("title").toString();
        }catch(IOException e){
        }
        return cid;
    }

    public void downDanMuXml(String filePath, String fileName, String document){
        File dir = new File(filePath);
        if (!dir.exists()){
            dir.mkdirs();
        }


        Matcher matcher = pattern.matcher(fileName);
        fileName= matcher.replaceAll("");
        File file = new File(filePath+File.separator+ fileName
                +".xml");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(document);
            fileWriter.flush();
            fileWriter.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrlOrBv() {
        return UrlOrBv;
    }

    public void setUrlOrBv(String urlOrBv) {
        UrlOrBv = urlOrBv;
    }
}
