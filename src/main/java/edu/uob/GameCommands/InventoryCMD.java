package edu.uob.GameCommands;

import edu.uob.GameEngine.GameModel;
import edu.uob.GameEntities.GameEntity;
import edu.uob.GameEntities.Player;

import java.util.ArrayList;

public class InventoryCMD extends PlayerCMD{
    public InventoryCMD(Player player, GameModel model) {
        super(player, model);
    }

    @Override
    public String interpretCMD() {
        StringBuilder allArtefacts = new StringBuilder();
        ArrayList<GameEntity> playerInventory = player.getInventory();
        if (playerInventory.isEmpty()) {
            allArtefacts.append("Your inventory is empty.");
        } else {
            allArtefacts.append("You have the following items:\n");
            for (GameEntity entity : playerInventory) {
                if (entity.getType().equals("artefacts")) {
                    allArtefacts.append(entity.getName() + " - " + entity.getDescription() + "\n");
                }
            }
        }
        return allArtefacts.toString();
    }
}
