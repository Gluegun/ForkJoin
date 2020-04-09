package Experiments;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ExampleWithRecursiveAction {


    public static void main(String[] args) throws IOException {

        ProductListGenerator generator = new ProductListGenerator();
        List<Product> products = generator.generate(10000);

        Task task = new Task(products, 0, products.size(), 0.20);
        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(task);

        while (!task.isDone()) {
            try {
                System.out.print("***********************\n");
                System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
                System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
                System.out.printf("Main: Experiments.Task count: %d\n", pool.getQueuedTaskCount());
                System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
                System.out.print("***********************\n");
                Thread.sleep(5);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        pool.shutdown();
        if (task.isCompletedNormally()) {
            System.out.println("Main: The process has finished normally");
        }

    }
}

class Product {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private double price;
}

class ProductListGenerator {

    public List<Product> generate(int size) {
        List<Product> ret = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Product product = new Product();
            product.setName("Experiments.Product " + i);
            product.setPrice(10);
            ret.add(product);
        }
        return ret;
    }
}

class Task extends RecursiveAction {

    private List<Product> products;
    private int first;
    private int last;

    private double increment;

    public Task(List<Product> products, int first, int last, double increment) {
        this.products = products;
        this.first = first;
        this.last = last;
        this.increment = increment;
    }

    @Override
    protected void compute() {
        if (last - first < 10) {
            updatePrices();
        } else {
            int middle = (last + first) / 2;
            System.out.printf("Experiments.Task: Pending tasks: %s\n", getQueuedTaskCount());
            Task t1 = new Task(products, first, middle + 1, increment);
            Task t2 = new Task(products, middle + 1, last, increment);
            invokeAll(t1, t2);
        }
    }

    private void updatePrices() {
        for (int i = first; i < last; i++) {
            Product product = products.get(i);
            product.setPrice(product.getPrice() * (1 + increment));
        }
    }

}

