package com.leo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtil {

    private JsoupUtil() {

    }

    private static final JsoupUtil instance = new JsoupUtil();

    public static JsoupUtil getInstance() {
        return instance;
    }

    /**
     * 将电影名称和评论写入文件
     *
     * @param name 电影名称
     * @param star 评论
     */
    public void writeFile(String name, String star) {
        File file = new File("C:\\temp\\review.txt");
        Writer writer = null;
        try {
            writer = new FileWriter(file, true);
            writer.write(star + "   " + name + "\r\n");
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getDoubanReview() {
        try {
            //只得到1-5页数据
            for (int i = 0; i < 5; i++) {
                String url = Constants.URL + Constants.START + String.valueOf(i * Constants.NUM);
                System.out.println(url);
                Connection connection = Jsoup.connect(url);
                Document document = connection.get();
                Elements ul = document.select("div.review-item"); // 得到ul标签
                Iterator<Element> ulIter = ul.iterator();
                while (ulIter.hasNext()) {
                    Element name = ulIter.next();
                    System.out.println(name.select("a.subject-title").first().text()+": "+name.select("span.main-title-rating").first().attr("title"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}