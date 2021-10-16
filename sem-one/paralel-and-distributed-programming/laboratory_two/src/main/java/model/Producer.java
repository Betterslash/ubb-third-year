package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Getter
@Setter
@RequiredArgsConstructor
public class Producer {
    private final Queue<Message> channel;
    private final ExecutorService executorService;
    private final Resource resource;
    private Message message;

    private void produce() {
        var result = 0L;
        var size = resource.getSize();
        var firstVector = resource.getFirstVector();
        var secondVector = resource.getSecondVector();
        for (int i = 0; i < size; i++) {
            synchronized (channel) {
                result = (long) firstVector.get(i) * secondVector.get(i);
                this.message = Message
                        .builder()
                        .id(UUID.randomUUID())
                        .message("Computed sucesfully...")
                        .result(result)
                        .build();
                System.out.printf("%s -> %s %d%n",
                        message.getId().toString(),
                        message.getMessage(),
                        message.getResult());
                channel.add(message);
                channel.notifyAll();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (channel){
            var exitMessage = Message.builder()
                    .message("EXIT")
                    .id(UUID.randomUUID())
                    .result(0L)
                    .build();
            channel.add(exitMessage);
            channel.notify();
        }
    }

    public void produceAsync() {
        executorService.submit(this::produce);
    }

}
