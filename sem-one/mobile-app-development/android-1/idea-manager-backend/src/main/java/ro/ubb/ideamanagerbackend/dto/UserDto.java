package ro.ubb.ideamanagerbackend.dto;

import lombok.*;
import ro.ubb.ideamanagerbackend.shared.UserRole;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    private String username;

    private String password;

    private String email;

    private LocalDate birthday;

    private UserRole role;
}
