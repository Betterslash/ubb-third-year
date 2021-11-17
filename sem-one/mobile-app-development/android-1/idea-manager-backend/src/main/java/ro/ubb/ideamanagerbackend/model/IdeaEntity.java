package ro.ubb.ideamanagerbackend.model;

import lombok.*;

import javax.persistence.*;

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
}
