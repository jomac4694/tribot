package scripts.VorkathAnnihilator.Actions.Banking;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.VorkathAnnihilator.Utils.ACamera;
import scripts.VorkathAnnihilator.Actions.Action;
import scripts.VorkathAnnihilator.Data.Variables;

public class WalkToVorkath extends Action {

    RSArea enterArea;
    RSArea boatArea;

    public WalkToVorkath(ACamera camera) {
        super(camera);
        enterArea = new RSArea(new RSTile(2271,4052, 0), new RSTile(2273,4047,0));
        boatArea = new RSArea(new RSTile(2276,4037,0), new RSTile(2279,4034,0));
    }
    @Override
    public boolean active() {
        return boatArea.contains(Player.getPosition()) || enterArea.contains(Player.getPosition());
    }

    @Override
    public void activate() {

        switch (getState()) {
            case WALK_TO_ENTRACE:
                Variables.setState("WALKING TO ICE");
                walkToEntrace();
                break;
            case ENTER:
                Variables.setState("ENTERING LAIR");
                enterVorkath();
                break;
        }
    }

    private enum SubState {
        WALK_TO_ENTRACE, ENTER
    }

    private SubState getState() {
        if (boatArea.contains(Player.getPosition())) {
            return SubState.WALK_TO_ENTRACE;
        }
        else {
            return SubState.ENTER;
        }
    }

    private void walkToEntrace() {
        if (WebWalking.walkTo(enterArea.getRandomTile())) {
            Timing.waitCondition(new Condition() {

                @Override
                public boolean active() {
                    General.sleep(100, 200);
                    return enterArea.contains(Player.getPosition());
                }
            }, General.random(1000, 1250));
        }
    }

    private void enterVorkath() {
        RSObject[] chunks = Objects.findNearest(20, 31990);
        if (chunks.length>=1) {
            if (chunks[0].click("Climb-over")) {
                General.sleep(1000, 2000);
                Timing.waitCondition(new Condition() {

                    @Override
                    public boolean active() {
                        General.sleep(500, 3000);
                        return Player.getAnimation() == -1;
                    }
                }, General.random(2000, 3000));
            }
        }
    }
}
