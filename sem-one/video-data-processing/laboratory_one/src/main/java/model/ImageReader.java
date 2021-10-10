package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Getter
@Setter
@AllArgsConstructor
public class ImageReader {

    public static BufferedReader initializeImage(String path){
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
