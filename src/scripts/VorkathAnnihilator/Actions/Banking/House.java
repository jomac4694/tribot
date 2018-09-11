package scripts.VorkathAnnihilator.Actions.Banking;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.VorkathAnnihilator.Utils.ACamera;
import scripts.VorkathAnnihilator.Actions.Action;
import scripts.VorkathAnnihilator.Data.Variables;

public class House extends Action {

    private RSArea houseArea;

    public House(ACamera camera) {
        super(camera);
        houseArea = new RSArea(new RSTile(11474, 5271, 1), new RSTile(11486, 5265, 1));
    }

    @Override
    public void activate() {
        switch (getState()) {
            case RESTORING_STATS:
                Variables.setState("RESTORING HEALTH");
                drinkFountain();
                break;
            case TELEPORTING:
                Variables.setState("TELEING TO CASTLE WARS");
                openTeleportInterface();
                useTeleportInterface();
                break;
        }
    }

    @Override
    public boolean active() {
        return Objects.findNearest(20, "Ornate rejuvenation pool").length >= 1;
    }

    public enum SubState {
        RESTORING_STATS, TELEPORTING
    }

    private SubState getState() {
        if (Skills.getCurrentLevel(Skills.SKILLS.PRAYER) < 99 || Skills.getCurrentLevel(Skills.SKILLS.HITPOINTS) < 99) {
            return SubState.RESTORING_STATS;
        } else {
            return SubState.TELEPORTING;
        }
    }

    private void drinkFountain() {
        RSObject[] fountain = Objects.findNearest(15, "Ornate rejuvenation pool");
        if (fountain.length >= 1) {
            if (fountain[0] != null) {
                if (DynamicClicking.clickRSObject(fountain[0], "Drink " + fountain[0].getDefinition().getName())) {
                    General.sleep(2000, 3000);
                }
            }
        }
    }

    private void openTeleportInterface() {
        RSObject[] box = Objects.findNearest(15, 29156);
        if (box.length >= 1) {
            if (box[0] != null) {
                if (box[0].click("Teleport")) {
                    General.sleep(3000, 5000);
                }
            }
        }
    }

    private void useTeleportInterface() {
        RSInterfaceChild inter = Interfaces.get(590).getChild(2);

        if (inter != null) {
            if (inter.click("Castle Wars"))
                General.sleep(4000, 5000);
        }

    }
}
