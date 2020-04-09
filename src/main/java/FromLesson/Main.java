package FromLesson;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {

        Node root = null;
        ForkJoinPool pool = new ForkJoinPool();
        Long sum = pool.invoke(new NodeValueSumCalculator(root));
        System.out.println(sum);


    }
}
