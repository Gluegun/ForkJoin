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
    private static Set<String> links = new HashSet<>();


    public Links(String url) {
        System.out.println(url);
        this.url = url;
    }


    @Override
    public Set<String> compute() {
        parsePage(url);
        List<Links> taskList = new ArrayList<>();
        for (String link : links) {
            Links task = new Links(link);
            task.fork();
            taskList.add(task);
        }

        for (Links link : taskList) {
            Set<String> join = link.join();
            links.addAll(join);
        }
        return links;
    }

    public Set<String> parsePage(String url) {
        Document doc;

        try {
            doc = Jsoup.connect(url).maxBodySize(0).get();
            Elements elements = doc.select("a[href]");

            for (Element element : elements) {
                String link = element.absUrl("href");
                if (link.startsWith("https://skillbox.ru/")) {
                    links.add(link);
                }
            }
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return links;
    }
}
