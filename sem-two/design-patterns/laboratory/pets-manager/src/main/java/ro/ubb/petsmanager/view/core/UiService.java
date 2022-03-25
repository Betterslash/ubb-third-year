package ro.ubb.petsmanager.view.core;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ro.ubb.petsmanager.controller.ApiController;
import ro.ubb.petsmanager.dto.AnimalDto;
import ro.ubb.petsmanager.dto.DogDto;
import ro.ubb.petsmanager.dto.LoginDto;
import ro.ubb.petsmanager.model.Animal;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

public interface UiService {
    DogDto add(DogDto resource);

    AnimalDto createAnimal(String type, String name, String race, String owner, Long age);

    @Getter
    @Setter
    @Builder
    class RequestResponse{
        private String body;

        public <S extends AnimalDto> S responseToObject(Class<S> type){
            return new Gson().fromJson(body, type);
        }

        public List<? extends Animal> responseToList(Class<? extends Animal[]> type) {
            var converter = new Gson();
            return Arrays.stream(converter.fromJson(this.body, type)).toList();
        }
    }


    <S extends AnimalDto> S add(S resource);
    ApiController.UserResponse<Object> login(LoginDto loginDto);
    HttpURLConnection createConnection(String path);
    RequestResponse readResponse(HttpURLConnection connection);
}
