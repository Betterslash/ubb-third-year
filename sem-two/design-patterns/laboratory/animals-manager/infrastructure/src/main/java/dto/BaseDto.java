package dto;

import lombok.Data;

@Data
public abstract class BaseDto {
    protected abstract String getRace();
    protected abstract String getBirthday();
}
