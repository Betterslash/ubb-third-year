import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Subsets {
    private final static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public int allSubSets(List<Integer> list) throws InterruptedException {
        var result = new AtomicInteger();
        var threads = new ArrayList<Thread>();
        for(int i = 0; i<=list.size(); i++) {
            int finalI = i;
            var thread = new Thread(() -> {
                try {
                    result.addAndGet(this.subSets(list, finalI));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads.add(thread);
        }
        threads.forEach(executor::submit);
        executor.shutdown();
        executor.awaitTermination(15000, TimeUnit.MILLISECONDS);
        return result.get();
    }

    private boolean checkSum(List<Integer> vector){
        var castedVector = vector.stream().toList();
        var sum = castedVector.stream().reduce(Integer::sum).orElse(0);
        return sum < 8;
    }

    private int subSets(List<Integer> list, int size) throws InterruptedException {


        var result = 0;
            for(int i = 0; i < list.size() - size + 1; i++) {
                var subset = new ArrayList<Integer>();
                for (int j = i; j < i + size - 1; j++) {
                    subset.add(list.get(j));
                }
                if (!(size==1 && i>0)) {
                    for (int j = i + size-1; j<list.size(); j++) {
                        var newsubset = new ArrayList<>(subset);
                        newsubset.add(list.get(j));
                        if(checkSum(newsubset)) {
                            System.out.println(newsubset + " by thread : " + Thread.currentThread().getId());
                            result += 1;
                        }
                    }
                }
            }

        return result;
    }
}