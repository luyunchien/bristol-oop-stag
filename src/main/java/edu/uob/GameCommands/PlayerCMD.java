package edu.uob.GameCommands;

import edu.uob.GameEngine.GameModel;
import edu.uob.GameEntities.GameEntity;
import edu.uob.GameEntities.Player;
import edu.uob.GameExceptions.GameException;
import edu.uob.GameExceptions.GameParseException;

import java.util.ArrayList;

public class PlayerCMD {
    ArrayList<String> tokens;
    GameModel model;
    Player player;

    public PlayerCMD(Player player, GameModel model) {
        this.player = player;
        this.model = model;
    }

    public String interpretCMD() throws GameException {
        return "";
    }

    public void checkCommandCompleted() throws GameParseException {
        if(tokens.size() <=1 ){
            throw new GameParseException("the command is incomplete.\n");
        }
    }

    public int getEntityIndexByType(ArrayList<GameEntity> entityList, String type, String builtinCommand) throws GameParseException {
        int count = 0;
        int index = 0;
        for(int i=0; i<entityList.size(); i++){
            if(entityList.get(i).getType().equals(type)) {
                if (tokens.contains(entityList.get(i).getName())) {
                    count++;
                    index = i;
                }
            }
        }
        if(count == 0){
            throw new GameParseException("cannot find the " + type);
        }else if(count > 1) {
            throw new GameParseException("there is more than one thing you can \'" + builtinCommand + "\' here - which one do you want?\n");
        }
        return index;
    }

}
