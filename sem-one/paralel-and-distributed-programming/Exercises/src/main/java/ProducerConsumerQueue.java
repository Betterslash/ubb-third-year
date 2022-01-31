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
        items.add(v);
        cv.signal();
        mtx.unlock();
    }
    public T dequeue() throws InterruptedException {
        mtx.lock(); //stmt 1
        while (items.isEmpty() && !isClosed){
            //place 1
            cv.await();
            //place 2

        }
        mtx.unlock();//stmt 2
        if(!items.isEmpty()){
            T ret = items.get(0);
            items.remove(0);
            //place 3
            return ret;
        }
        //place 4
        return null;
    }

    public void close(){
        mtx.lock();
        isClosed = true;
        cv.signalAll();
        mtx.unlock();
    }
}
