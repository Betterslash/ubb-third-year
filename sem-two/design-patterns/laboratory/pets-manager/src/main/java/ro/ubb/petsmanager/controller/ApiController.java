package ro.ubb.petsmanager.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class ApiController {
    @Getter
    @Setter
    @Builder
    public static class UserResponse<T> implements Serializable {
        private T response;
    }
}
