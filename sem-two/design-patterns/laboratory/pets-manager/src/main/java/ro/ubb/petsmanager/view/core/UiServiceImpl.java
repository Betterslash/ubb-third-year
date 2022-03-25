package ro.ubb.petsmanager.view.core;

import com.google.gson.Gson;
import io.micrometer.core.lang.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import ro.ubb.petsmanager.controller.ApiController;
import ro.ubb.petsmanager.dto.AnimalDto;
import ro.ubb.petsmanager.dto.CatDto;
import ro.ubb.petsmanager.dto.DogDto;
import ro.ubb.petsmanager.dto.LoginDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
public class UiServiceImpl implements UiService {

    @Value("${ui.service.url}")
    private String apiUrl;

    @Override
    public DogDto add(DogDto resource) {

        try {
            var connection = createConnection("dogs");
            connection.setRequestMethod(HttpMethod.POST.name());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            try(var os = connection.getOutputStream()) {
                var input = new Gson().toJson(resource).getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            var response = readResponse(connection);
            return response.responseToObject(DogDto.class);
        } catch (ProtocolException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    @Override
    public AnimalDto createAnimal(String type, String name, String race, String owner, Long age) {
        switch (type) {
            case "Dog" -> {
                return DogDto.builder()
                        .name(name)
                        .age(age)
                        .race(race)
                        .race(race)
                        .owner(owner)
                        .available(true)
                        .build();
            }
            case "Cat" -> {
                return CatDto
                        .builder()
                        .name(name)
                        .age(age)
                        .race(race)
                        .race(race)
                        .owner(owner)
                        .available(true)
                        .build();
            }
            default -> throw new RuntimeException("Type is invalid !!");
        }
    }

    @Override
    public <S extends AnimalDto> S add(S resource) {
        return null;
    }

    @Override
    public ApiController.UserResponse<Object> login(LoginDto loginDto) {
        try {
            var connection = this.createConnection("account/login");
            connection.setRequestMethod(HttpMethod.POST.name());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            try(var os = connection.getOutputStream()) {
                var input = new Gson().toJson(loginDto).getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return readLoginResponse(connection);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        return ApiController.UserResponse
                .builder()
                .response(false)
                .build();
    }

    @Override
    public HttpURLConnection createConnection(@Nullable String path) {
        try {
            var url = new URL(String.format("%s%s", apiUrl, path));
            return (HttpURLConnection) url.openConnection();
        }catch (IOException e){
            throw new RuntimeException(Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public RequestResponse readResponse(HttpURLConnection connection){
        try( var in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

            var lines = in.lines()
                    .toList()
                    .stream().reduce((a, b) -> a + b)
                    .orElse("");
            in.close();
            return RequestResponse.builder()
                    .body(lines)
                    .build();
        }catch (IOException e){
            throw new RuntimeException(Arrays.toString(e.getStackTrace()));
        }
    }

    public ApiController.UserResponse<Object> readLoginResponse(HttpURLConnection connection){
        try( var in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            var converter = new Gson();
            var lines = in.lines()
                    .toList()
                    .stream().reduce((a, b) -> a + b)
                    .orElse("");
            in.close();
            return converter.fromJson(lines, ApiController.UserResponse.class);
        }catch (IOException e){
            throw new RuntimeException(Arrays.toString(e.getStackTrace()));
        }
    }

}
