package edu.uob.GameCommands;

import edu.uob.GameEngine.GameModel;
import edu.uob.GameEntities.Location;
import edu.uob.GameEntities.Player;
import edu.uob.GameExceptions.GameExecuteException;
import edu.uob.GameExceptions.GameParseException;

import java.util.ArrayList;

public class GotoCMD extends PlayerCMD{
    public GotoCMD(Player player, GameModel model, ArrayList<String> tokens) {
        super(player, model);
        this.tokens = tokens;
    }

    @Override
    public String interpretCMD() throws GameExecuteException, GameParseException {
        checkCommandCompleted();
        Location currentLocation = model.getLocationList().get(player.getCurrentLocation());
        ArrayList<String> paths = currentLocation.getPaths();
        int index = getPathIndex(paths);

        String locationName = paths.get(index);
        player.setCurrentLocation(locationName);
        return "You went to " + locationName + "\n";
    }

    private int getPathIndex(ArrayList<String> paths) throws GameExecuteException {
        int index = 0;
        int count = 0;
        for(int i=0; i<paths.size(); i++){
            if(tokens.contains(paths.get(i))){
                count++;
                index = i;
            }
        }
        if(count == 0){
            throw new GameExecuteException("there is no path to this place");
        }else if(count > 1){
            throw new GameExecuteException("you can only go to one place each time");
        }
        return index;
    }
}
