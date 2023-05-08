package edu.uob.GameCommands;

import edu.uob.GameEngine.GameModel;
import edu.uob.GameEntities.GameEntity;
import edu.uob.GameEntities.Location;
import edu.uob.GameEntities.Player;
import edu.uob.GameExceptions.GameParseException;

import java.util.ArrayList;

public class DropCMD extends PlayerCMD{
    public DropCMD(Player player, GameModel model, ArrayList<String> tokens) {
        super(player, model);
        this.tokens = tokens;
    }

    @Override
    public String interpretCMD() throws GameParseException {
        checkCommandCompleted();
        ArrayList<GameEntity> playerInventory = player.getInventory();
        int index = getEntityIndexByType(playerInventory, "artefacts", "drop");
        GameEntity artefact = player.getInventory().get(index);
        Location currentLocation = model.getLocationList().get(player.getCurrentLocation());
        currentLocation.addEntity(artefact);
        String artefactName = playerInventory.get(index).getName();
        player.removeFromInventory(artefactName);
        return "You dropped a " + artefactName + "\n";
    }
}
