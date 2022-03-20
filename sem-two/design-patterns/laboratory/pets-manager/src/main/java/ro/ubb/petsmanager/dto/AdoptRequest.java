package ro.ubb.petsmanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdoptRequest {
    private String animalId;
    private String username;
}
