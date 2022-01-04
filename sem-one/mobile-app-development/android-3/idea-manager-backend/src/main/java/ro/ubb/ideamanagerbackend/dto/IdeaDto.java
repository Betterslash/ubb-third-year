package ro.ubb.ideamanagerbackend.dto;

import lombok.*;
import ro.ubb.ideamanagerbackend.shared.IdeaDomain;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdeaDto {

    private Long id;

    private String title;

    private String text;

    private Long neededBudget;

    private Long currentBudget;

    private Integer rating;

    private Long user;

    private IdeaDomain domain;

    private LocalDate localDate;

    private Long rateCount;
}
