package ForHomeWork;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {


        String url = "https://skillbox.ru/";

        Document doc = Jsoup.connect(url).get();

        Elements coursesSection = doc.select("section.tabs-list.about-page__tabs");

        for (Element courseSection : coursesSection) {
            Elements courses = courseSection.select("li.tabs-list__item");

            for (Element course : courses) {
                String newUrl = course.select("a").attr("href");
                String courseName = course.text();
                newUrl = url + newUrl.substring(1);
                System.out.println(courseName + ": " + newUrl);
            }
        }
    }
}
