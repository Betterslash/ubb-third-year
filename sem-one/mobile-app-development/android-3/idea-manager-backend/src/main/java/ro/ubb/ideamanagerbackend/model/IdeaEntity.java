package ro.ubb.ideamanagerbackend.model;

import lombok.*;
import ro.ubb.ideamanagerbackend.shared.IdeaDomain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "idea")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdeaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String text;

    private Long neededBudget;

    private Long currentBudget;

    private Integer rating;

    @ManyToOne
    private UserEntity user;

    private IdeaDomain domain;

    private LocalDate localDate;

    private Long rateCount;
}
