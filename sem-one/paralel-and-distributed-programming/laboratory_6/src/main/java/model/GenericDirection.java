package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class GenericDirection<T>{

    private final List<T> in = new ArrayList<>();
    private final List<T> out = new ArrayList<>();
}
