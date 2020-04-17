package HomeWork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        String url = "https://skillbox.ru";
        Links links = new Links(url);
        Set<String> invoke = new ForkJoinPool().invoke(links);

        System.out.println(invoke.size());
        System.out.println("Время работы программы: " + (System.currentTimeMillis() - start) + " мс.");


        Path filePath = Paths.get("src/main/resources/siteMap.txt");

        try {
            Files.write(filePath, invoke, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
