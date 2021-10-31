package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Game extends Product{
    public Game() {
        super(Game.class.getName(), 50);
    }
}
