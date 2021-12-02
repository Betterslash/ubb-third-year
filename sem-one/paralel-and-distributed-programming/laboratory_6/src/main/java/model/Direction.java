package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class Direction {
    private final List<String> in = new ArrayList<>();
    private final List<String> out = new ArrayList<>();
}
