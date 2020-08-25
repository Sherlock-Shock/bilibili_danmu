package com.lee.bilibili_danmu;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author sherlock
 * @date 2020/8/25 17:00
 */
public class DanMuAnalysis {
    String filePath="";
    int count;
    DanMuAnalysis(String filePath){
        this.filePath = filePath;
        this.count=0;
    }
    //File files1 = new File("D:\\pachongtupian\\555");
    File files2 = new File(filePath);
    Set<String> expectedNature = new HashSet<String>() {{
        add("n");add("v");add("vd");add("vn");add("vf");
        add("vx");add("vi");add("vl");add("vg");
        add("nt");add("nz");add("nw");add("nl");
        add("ng");add("userDefine");add("wh");
    }};
    Map<String,Integer> map = new HashMap<>();
    Document document = null;
    public void analysis(){
        File files = new File(this.filePath);
        File[] xmlFiles = files.listFiles();
        try{
            for (File f:xmlFiles
            ) {
                if (!("xml".equals(f.getName().substring(f.getName().lastIndexOf(".")+1)))){
                    continue;
                }
                document = Jsoup.parse(f,"utf-8");
                Elements elements = document.getElementsByTag("d");
                StringBuilder str = new StringBuilder();
                System.out.println("正在解析文件："+f.getName());
                for (Element e:elements
                ) {
                    str.append(e.text().toString());
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
                count++;
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
        System.out.println("共解析："+count+"个文件");
        for(Map.Entry<String,Integer> mapping:list){
            System.out.println(mapping.getKey()+":"+mapping.getValue());
        }
    }


}
