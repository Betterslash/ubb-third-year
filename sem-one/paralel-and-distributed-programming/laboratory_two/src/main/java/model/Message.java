package model;

import lombok.*;

import java.util.UUID;

@Data
@Builder
public class Message {
    private UUID id;
    private String message;
    private Long result;
}
