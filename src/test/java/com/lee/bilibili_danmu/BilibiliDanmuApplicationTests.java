package com.lee.bilibili_danmu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.*;

@SpringBootTest
class BilibiliDanmuApplicationTests {

    @Test
    void contextLoads() {
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("v");add("vd");add("vn");add("vf");
            add("vx");add("vi");add("vl");add("vg");
            add("nt");add("nz");add("nw");add("nl");
            add("ng");add("userDefine");add("wh");
        }};
        Map<String,Integer> map = new HashMap<>();
        Document document = null;

        File filePath = new File("D:\\pachongtupian\\aochangzhang");

        File[] xmlFiles = filePath.listFiles();

        try{
            for (File f:xmlFiles
            ) {
                document = Jsoup.parse(f,"utf-8");
                Elements elements = document.getElementsByTag("d");
                StringBuilder str = new StringBuilder();
                System.out.println("正在解析文件："+f.getName());
                for (Element e:elements
                ) {
                    str.append(e.text());
                }
                Result result = ToAnalysis.parse(str.toString());
                List<Term> terms = result.getTerms();
                for (int i = 0; i < terms.size(); i++) {
                    String word = terms.get(i).getName();
                    String natureStr = terms.get(i).getNatureStr(); //拿到词性
                    if(expectedNature.contains(natureStr)) {
                        //System.out.println(word + ":" + natureStr);
                        if (map.containsKey(word)){
                            int count = map.get(word);
                            map.put(word,count+1);
                        }else{
                            map.put(word,1);
                        }
                    }
                }
                System.out.println("解析完毕："+f.getName());
            }

        }catch (IOException e){}

        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });

        for(Map.Entry<String,Integer> mapping:list){
            System.out.println(mapping.getKey()+":"+mapping.getValue());
        }

    }

}
