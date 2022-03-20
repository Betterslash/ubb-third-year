package ro.ubb.petsmanager.view.core;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ro.ubb.petsmanager.controller.ApiController;
import ro.ubb.petsmanager.dto.LoginDto;
import ro.ubb.petsmanager.model.Animal;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

public interface UiService {
    @Getter
    @Setter
    @Builder
    class RequestResponse{
        private String body;
        public List<? extends Animal> responseToList(Class<? extends Animal[]> type) {
            var converter = new Gson();
            return Arrays.stream(converter.fromJson(this.body, type)).toList();
        }
    }

    ApiController.UserResponse<Object> login(LoginDto loginDto);
    HttpURLConnection createConnection(String path);
    RequestResponse readResponse(HttpURLConnection connection);
}
