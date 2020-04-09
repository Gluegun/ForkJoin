package ForHomeWork;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.RecursiveAction;

public class SiteMapCreator extends RecursiveAction {


    @Override
    protected void compute() {

    }

    public void research(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();

        Elements courses = doc.select("sections.tabs-list.about-page_tabs");

    }
}
