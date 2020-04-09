package ForHomeWork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WriterToFileWithTabs {


    private static List<String> listWithTab(List<String> list) {
        List<String> newList = new ArrayList<>();
        newList.add(list.get(0) + "\n");
        String string = "";
        StringBuilder tab = new StringBuilder();
        for (int i = 1; i < list.size(); i++) {
            tab.append("\t");
            string = tab + list.get(i) + "\n";
            newList.add(string);
        }
        return newList;
    }

    public static void writeListWithTabsToFile(Path filePath, List<String> listWithStrings) {

        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        listWithStrings = listWithTab(listWithStrings);

        for (String string : listWithStrings) {
            try {
                Files.write(filePath, string.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void writeListWithTabsToFile(Path filePath, String... strings) {

        List<String> listWithStrings = Arrays.asList(strings);

        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        listWithStrings = listWithTab(listWithStrings);

        for (String string : listWithStrings) {
            try {
                Files.write(filePath, string.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}


