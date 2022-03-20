package ro.ubb.petsmanager.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class LoginDto extends BaseDto{
    private String username;
    private String password;
}
