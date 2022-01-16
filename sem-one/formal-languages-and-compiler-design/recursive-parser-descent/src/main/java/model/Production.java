package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@ToString
public class Production {
    private String leftHandside;
    private List<List<String>> rightHandside;

    public static Production parse(String resource){
        var words = Arrays.stream(resource.split(" -> ")).toList();
        var leftHandside = words.get(0);
        var rightHandside = Arrays.stream(words.get(1)
                .split(" \\| "))
                .toList()
                .stream().map(e -> Arrays.stream(e.split(" ")).toList())
                .collect(Collectors.toList());
        return Production.builder()
                .leftHandside(leftHandside)
                .rightHandside(rightHandside)
                .build();
    }
}
