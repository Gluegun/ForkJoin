package HomeWork;

import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {

        String url = "https://skillbox.ru";
        Links links = new Links(url);
        Set<String> invoke = new ForkJoinPool().invoke(links);

    }
}
