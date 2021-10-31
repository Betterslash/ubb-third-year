package util;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
public class HandsidesGrammarPair extends HandsidePair<String, List<String>>{
}
