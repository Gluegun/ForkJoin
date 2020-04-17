package HomeWork;

import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class Links extends RecursiveTask<Set<String>> {


    @Getter
    private String url;

    @Getter
    private static Set<String> visitedLinks = new HashSet<>();

    public Links(String url) {
        System.out.println(url);
        this.url = url;
    }


    @Override
    public Set<String> compute() {
        visitedLinks.add(url);
        Set<String> linksInsideCompute = parsePage(url);

        List<Links> taskList = new ArrayList<>();

        for (String link : linksInsideCompute) {
            Links links = new Links(link);
            links.fork();
            taskList.add(links);
        }
        taskList.forEach(Links::join);
        return visitedLinks;
    }

    public Set<String> parsePage(String url) {
        Document doc;
        Set<String> links = new HashSet<>();

        try {
            doc = Jsoup.connect(url).maxBodySize(0).get();
            Elements elements = doc.select("a[href]");

            for (Element element : elements) {
                String link = element.absUrl("href");
                if (checkURL(link) && addNewURL(link)) {
                    links.add(link);
                }
            }
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return links;
    }

    private synchronized boolean addNewURL(String url) {
        return visitedLinks.add(url);
    }

    private static boolean checkURL(String url) {
        return url.startsWith("https://skillbox.ru/") && url.endsWith("/");
    }
}
