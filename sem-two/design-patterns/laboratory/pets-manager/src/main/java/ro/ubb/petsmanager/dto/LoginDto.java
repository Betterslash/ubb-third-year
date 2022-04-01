package ro.ubb.petsmanager.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto extends BaseDto{
    private String username;
    private String password;
}
