package com.lee.bilibili_danmu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonAllDanMu implements DanMu{
    String filePath;
    String spaceUrl;
    String bvListUrl = "https://api.bilibili.com/x/space/arc/search?mid=176037767&ps=30&tid=0&pn=2&order=pubdate&jsonp=jsonp";

    PersonAllDanMu(String filePath,String spaceUrl){
        this.filePath = filePath;
        this.spaceUrl = spaceUrl;
    }

    private String getMid(){
        String mid;
        if (spaceUrl.contains("?")){
            mid = spaceUrl.substring(spaceUrl.lastIndexOf("/")+1,spaceUrl.lastIndexOf("?"));
        }else{
            mid = spaceUrl.substring(spaceUrl.lastIndexOf("/")+1);
        }
        return mid;
    }

    public int getPages(){
        Document document = null;
        String mid = getMid();
        int count =0;
        try {
            document = Jsoup.connect("https://api.bilibili.com/x/space/arc/search?mid="
                    +mid+"&ps=30&tid=0&pn=1&order=pubdate&jsonp=jsonp")
                    .timeout(10000)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                    .get();
            Elements e = document.select("body");
            JSONObject jsonObject = JSONObject.parseObject(e.text().toString());
            //System.out.println(jsonObject);
            //System.out.println(jsonObject.getJSONObject("data").getJSONObject("page").get("count").toString());
            count = Integer.parseInt(jsonObject.getJSONObject("data").getJSONObject("page").get("count").toString());

        }catch (IOException e){

        }
        return (int)Math.ceil((double) count/30);
    }

   public List<String> getBvList(){
        List<String> bvList = new ArrayList<>();
        String mid = getMid();
        int page = getPages();
       Document document = null;
       for (int i = 1; i < page; i++) {
           try {
               document = Jsoup.connect("https://api.bilibili.com/x/space/arc/search?mid="
                       +mid+"&ps=30&tid=0&pn="
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
                   //System.out.println(jsonStr.getJSONObject(j).get("title"));
                   //System.out.println(jsonStr.getJSONObject(j).get("bvid"));
                   bvList.add(jsonStr.getJSONObject(j).get("bvid").toString());
               }
           }catch (IOException e){

           }
       }
           return bvList;
   }

    @Override
    public void getDanMu() {
        BvDanMu bvDanMu = null;
        List<String> bvList = getBvList();
        for (String BV:bvList
             ) {
            bvDanMu = new BvDanMu(filePath,BV);
            bvDanMu.getDanMu();
        }
    }
}
