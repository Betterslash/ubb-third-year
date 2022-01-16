package service;

import model.Grammar;

import java.util.List;

public abstract class Parser {
    protected Grammar grammar;

    protected Parser(Grammar grammar) {
        this.grammar = grammar;
    }

    protected abstract List<String> getDerivations();
}
