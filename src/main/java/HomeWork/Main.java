package HomeWork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();
        String url = "https://skillbox.ru/";
        Links links = new Links(url);
        Set<String> invoke = new ForkJoinPool().invoke(links);

        System.out.println(invoke.size());
        System.out.println("Время работы программы: " + (System.currentTimeMillis() - start) + " мс.");

        Path filePath = Paths.get("src/main/resources/siteMap.txt");

        write(invoke,filePath);

    }

    public static void write(Set<String> links, Path filePath) {

        List<String> newStrings = new LinkedList<>();

        for (String string : links) {
            StringBuilder builder = new StringBuilder();
            string = string.substring(string.indexOf("skill"));
            String[] split = string.split("/");

            builder.append("\t".repeat(split.length));
            builder.append(string);
            builder.insert(builder.indexOf("s"), "https://");
            newStrings.add(builder.toString());
        }

        for (int i = 0; i < newStrings.size(); i++) {
            String string = newStrings.get(i);
            string = string.substring(1);
            newStrings.set(i, string);
        }

        try {
            Files.write(filePath, newStrings, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
