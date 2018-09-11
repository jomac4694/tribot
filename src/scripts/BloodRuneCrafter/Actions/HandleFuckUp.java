package scripts.BloodRuneCrafter.Actions;

import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Player;
import scripts.BloodRuneCrafter.Data.Constants;
import scripts.BloodRuneCrafter.Utils.Walker;

public class HandleFuckUp extends Action {

    @Override
    public void activate() {
        Walker.walkTo(Constants.OBS1_AREA_SOUTH.getRandomTile(), new Condition() {

            @Override
            public boolean active() {
                return Constants.OBS1_AREA_SOUTH.contains(Player.getPosition());
            }
        });
    }

    @Override
    public boolean active() {
        return Constants.FUCK_UP_AREA.contains(Player.getPosition());
    }
}
