package HomeWork;

import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class Links extends RecursiveTask<Set<String>> {

    @Getter
    private String url;

    @Getter
    private Set<String> links;

    public Links(String url) {
        System.out.println(url);
        this.url = url;
    }

    @Override
    public Set<String> compute() {

    }

    public Set<String> parsePage(String url) {
        Document doc;
        links = new HashSet<>();

        try {
            doc = Jsoup.connect(url).maxBodySize(0).get();
            Elements elements = doc.select("a[href]");

            for (Element element : elements) {
                String link = element.absUrl("element");
                if (link.startsWith("https://skillbox.ru/")) {
                    links.add(link);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return links;
    }
}
