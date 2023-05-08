package edu.uob.GameEngine;

import java.util.ArrayList;
import java.util.Arrays;

import edu.uob.GameCommands.PlayerCMD;
import edu.uob.GameEntities.*;
import edu.uob.GameCommands.*;
import edu.uob.GameExceptions.GameException;

public class GameController {
    GameModel model;
    PlayerCMD playerCMD;
    static ArrayList<String> builtIns = new ArrayList<>(Arrays.asList("inventory", "inv", "get", "drop", "goto", "look"));

    public GameController(GameModel model){
        this.model = model;
    }

    public String parseCommand(String command) throws GameException {
        GameTokenizer tokenizer = new GameTokenizer(command);

        ArrayList<String> tokens =  tokenizer.splitIntoTokens();
        model.setCurrentPlayer(tokenizer.getPlayerName());
        Player player = model.getPlayerByName(tokenizer.getPlayerName());

        if(!checkOnlyOneBuiltinTrigger(tokens,tokenizer)) throw new GameException("Cannot match action, please try again.\n");

        switch (categorizeCommand(tokens)) {
            case "inventory", "inv" -> playerCMD = new InventoryCMD(player, model);
            case "get" -> playerCMD = new GetCMD(player, model, tokens);
            case "drop" -> playerCMD = new DropCMD(player, model, tokens);
            case "goto" -> playerCMD = new GotoCMD(player, model, tokens);
            case "look" -> playerCMD = new LookCMD(player, model);
            case "dynamic" -> {
                DynamicActionParser actionParser = new DynamicActionParser(model, tokens, tokenizer.getCommandsWithoutPlayer());
                GameAction action = actionParser.getAction();
                playerCMD = new DynamicCMD(player, model, action);
            }
            default -> throw new GameException("invalid command.\n");
        }
        return playerCMD.interpretCMD();
    }

    private String categorizeCommand(ArrayList<String> tokens){
        for(String token : tokens){
            if(builtIns.contains(token)){
                return token;
            }
        }
        return "dynamic";
    }

    private boolean checkOnlyOneBuiltinTrigger(ArrayList<String> tokens, GameTokenizer tokenizer) throws GameException {
        ArrayList<String> potentialBuiltinTriggers = new ArrayList<>();
        for(String token : tokens){
            if(builtIns.contains(token)){
                potentialBuiltinTriggers.add(token);
            }
        }

        DynamicActionParser parser = new DynamicActionParser(model, tokens, tokenizer.getCommandsWithoutPlayer());

        if(potentialBuiltinTriggers.size() == 0) {
            if(parser.getValidActionNum() == 1) {
                return true;
            } else if(parser.getValidActionNum() > 1){
                throw new GameException("There are more than one possible actions, please specify.\n");
            }
        } else if (potentialBuiltinTriggers.size() == 1){
            if(parser.getValidActionNum() == 0){
                return true;
            }else throw new GameException("There are more than one possible actions, please specify.\n");
        } else throw new GameException("There are more than one possible actions, please specify.\n");
        return false;
    }
}
