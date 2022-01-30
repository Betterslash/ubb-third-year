import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerQueue<T> {
    ArrayList<T> items = new ArrayList<>();
    boolean isClosed = false;
    Lock mtx = new ReentrantLock();
    Condition cv = mtx.newCondition();

    public void enqueue(T v){
        mtx.lock();
        //
        items.add(v);
        cv.signal();
        //System.out.println("Unlock cv -> ");
        mtx.unlock();
    }
    // [5]
    // enque(3)
    // dequeue()
    // excepted => [2, 3]
    /*
        1
        nrCols = 3 / 2 => 2
        1 => 0, 0;
             0, 1;
             0, 2;
             1, 0;
             1, 1;
             1, 2;
             2, 0;
             2, 1;
             2, 2;
        2 => 2, 0;
             2, 1;
             2, 2;
             3, 0;
             3, 1;
             3, 2;

    *  1 2 3   2 3 4
    *  1 2 3   2 3 4
    *  1 2 3   2 3 4
    *
    * */

    public T dequeue() throws InterruptedException {
        mtx.lock();
        while (items.isEmpty() && !isClosed){
            //System.out.println("Before await -> " + mtx.toString());
            mtx.unlock();
            cv.await();
            mtx.lock();
            //System.out.println("After await -> " + mtx.toString());
        }
        mtx.unlock();
        //
        if(!items.isEmpty()){
            T ret = items.get(0);
            items.remove(0);
            return ret;
        }
        return null;
    }

    public void close(){
        mtx.lock();
        isClosed = true;
        cv.signalAll();
        mtx.unlock();
    }
}
