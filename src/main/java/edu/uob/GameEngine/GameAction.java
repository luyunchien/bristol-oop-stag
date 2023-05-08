package edu.uob.GameEngine;

import edu.uob.GameExceptions.GameException;

import java.util.ArrayList;

public class GameAction {
    private ArrayList<String> subjects = new ArrayList<>();
    private ArrayList<String> consumed = new ArrayList<>();
    private ArrayList<String> produced = new ArrayList<>();
    private String narration;

    public void addAttributes(String type, String attributeName) throws GameException {
        switch (type) {
            case "subjects" -> subjects.add(attributeName);
            case "consumed" -> consumed.add(attributeName);
            case "produced" -> produced.add(attributeName);
            default -> throw new GameException("unrecognized action attribute.\n");
        }
    }

    public void addNarration(String narration){
        this.narration = narration;
    }

    public String getNarration(){
        return narration;
    }

    public ArrayList<String> getConsumed() {
        return consumed;
    }

    public ArrayList<String> getProduced() {
        return produced;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }
}
