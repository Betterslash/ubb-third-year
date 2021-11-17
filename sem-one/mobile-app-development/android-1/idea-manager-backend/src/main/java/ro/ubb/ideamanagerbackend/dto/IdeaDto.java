package ro.ubb.ideamanagerbackend.dto;

import lombok.*;

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
}
