package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Consumer {
    private Long sum;
    private final Queue<Message> channel;
    private final ExecutorService executorService;
    public void consume(){
        executorService.submit(() -> {
            while(true){
                synchronized (channel){
                   while (channel.size() == 0){
                       try {
                           channel.wait();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
                    var result = channel.poll();
                   if(!Objects.equals(result.getMessage(), "EXIT")){
                       sum+=result.getResult();
                       System.out.println(result);
                   }else {
                       break;
                   }
                }
            }
        });

    }
}
