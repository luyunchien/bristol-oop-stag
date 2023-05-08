package edu.uob.GameCommands;

import edu.uob.GameEngine.GameModel;
import edu.uob.GameEntities.GameEntity;
import edu.uob.GameEntities.Location;
import edu.uob.GameEntities.Player;
import edu.uob.GameExceptions.GameParseException;

import java.util.ArrayList;

public class GetCMD extends PlayerCMD{

    public GetCMD(Player player, GameModel model, ArrayList<String> tokens) {
        super(player, model);
        this.tokens = tokens;
    }

    @Override
    public String interpretCMD() throws GameParseException {
        checkCommandCompleted();
        Location currentLocation = model.getLocationList().get(player.getCurrentLocation());
        ArrayList<GameEntity> entityList = currentLocation.getEntityList();

        // Check if current location has the artefact to be picked up
        int index = getEntityIndexByType(entityList, "artefacts", "get");

        GameEntity artefact = entityList.get(index);
        player.addToInventory(artefact);
        String artefactName = entityList.get(index).getName();
        currentLocation.removeEntity(artefactName);
        return "You picked up a " + artefactName + "\n";
    }

}
