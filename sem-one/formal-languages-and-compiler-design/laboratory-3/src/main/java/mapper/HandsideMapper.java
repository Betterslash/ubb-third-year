package mapper;

import util.HandSideAutomataPair;
import util.HandsidesGrammarPair;

import java.util.ArrayList;
import java.util.List;

public class HandsideMapper {
    public static HandsidesGrammarPair fromAutomataPairToGrammarPair(HandSideAutomataPair automataPair){
        var leftHandside = automataPair.leftHandside.getState();
        var rightHandSide = new ArrayList<>(List.of(automataPair.leftHandside.getRoute() + automataPair.rightHandside));
        return HandsidesGrammarPair.builder()
                .leftHandside(leftHandside)
                .rightHandside(rightHandSide)
                .build();
    }
    public static HandsidesGrammarPair fromAutomataPairFinalStateToGrammarPair(HandSideAutomataPair automataPair){
        var leftHandside = automataPair.leftHandside.getState();
        var rightHandSide = new ArrayList<>(List.of(automataPair.leftHandside.getRoute()));
        return HandsidesGrammarPair.builder()
                .leftHandside(leftHandside)
                .rightHandside(rightHandSide)
                .build();
    }

    public static HandSideAutomataPair fromGrammarPairToAutomataPair(HandsidesGrammarPair grammarPair){
        var leftHandside = grammarPair.leftHandside;
        return null;
    }
}
