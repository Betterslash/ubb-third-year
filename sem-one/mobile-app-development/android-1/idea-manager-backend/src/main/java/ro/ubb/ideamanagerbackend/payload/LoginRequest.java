package ro.ubb.ideamanagerbackend.payload;

import lombok.Data;

@Data
public class LoginRequest {
    public String username;
    public String password;
}
