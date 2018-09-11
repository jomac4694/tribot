package scripts.VorkathAnnihilator.Actions.Banking;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.VorkathAnnihilator.Utils.ACamera;
import scripts.VorkathAnnihilator.Actions.Action;
import scripts.VorkathAnnihilator.Data.Variables;

public class Bank extends Action {
    RSArea bankArea;

    private int TELE_ANIMATION = 714;
    public Bank(ACamera camera) {
        super(camera);
        bankArea = new RSArea(new RSTile(2443, 3082, 0), new RSTile(2438, 3097, 0));
    }
    @Override
    public boolean active() {
        return bankArea.contains(Player.getPosition());
    }

    @Override
    public void activate() {
        switch (getState()) {

            case OPEN_BANK:
                Variables.setState("AT BANK, OPENING NOW");
                openBank();
                break;
            case WITHDRAWING:
                Variables.setState("WITHDRAWING SUPPLIES");
                withdraw();
                break;
            case DEPOSITING:
                Variables.setState("DEPOSITING SUPPLIES");
                deposit();
                break;
            case CLOSE_BANK:
                Variables.setState("CLOSING BANK");
                Banking.close();
                break;
            case TELEPORTING:
                teleport();
                break;
        }
    }

    private enum SubState {
        OPEN_BANK, DEPOSITING, WITHDRAWING, TELEPORTING, CLOSE_BANK
    }

    private SubState getState() {
        if (!Banking.isBankScreenOpen() && !inventReady()) {
            return SubState.OPEN_BANK;
        } else if (Banking.isBankScreenOpen() && Inventory.getCount(22124) > 0) {
            return SubState.DEPOSITING;
        } else if (Banking.isBankScreenOpen() && Inventory.getCount(22124) == 0 && !inventReady()) {
            return  SubState.WITHDRAWING;
        } else if (Banking.isBankScreenOpen() && inventReady()) {
            return SubState.CLOSE_BANK;
        } else {
            return SubState.TELEPORTING;
        }
    }

    private boolean inventReady() {
        return Inventory.getCount(22209) == 1 && Inventory.getCount(12913) == 1
                && Inventory.getCount(2444) == 1 && Inventory.getCount(2434) == 2
                && Inventory.getCount(385) == 20;
    }

    private void openBank() {
        RSObject[] bank = Objects.findNearest(50, "Bank chest");
        if (bank.length >= 1) {
            if (!bank[0].isOnScreen()) {
                Camera.turnToTile(bank[0].getPosition());
            }
            if (bank[0].click("Use")) {
                Timing.waitCondition(new Condition() {

                    @Override
                    public boolean active() {
                        General.sleep(100, 300);
                        return Banking.isBankScreenOpen();
                    }
                }, General.random(2000, 3000));
            }
        }
    }


    private void teleport() {
        if (Inventory.find(13132)[0].click("Teleport")) {
            General.sleep(1000, 1500);
            Timing.waitCondition(new Condition() {

                @Override
                public boolean active() {
                    return !bankArea.contains(Player.getPosition());
                }
            }, General.random(2000, 2750));
        }
    }

    private void deposit() {
        Banking.depositAllExcept(21946, 13132, 12791,21944,385);
    }

    private void withdraw() {
        if (Inventory.getCount(2444) == 0) Banking.withdraw(1, 2444);
        if (Inventory.getCount(12913) == 0) Banking.withdraw(1, 12913);
        if (Inventory.getCount(22209) == 0) Banking.withdraw(1, 22209);
        if (Inventory.getCount(2434) == 0) Banking.withdraw(2, 2434);
        if (Inventory.getCount(385) < 20) {
            if (Banking.withdraw(20, 385)) {
                Timing.waitCondition(new Condition() {

                    @Override
                    public boolean active() {
                        General.sleep(300, 500);
                        return Inventory.getCount(385) >= 20;
                    }
                }, General.random(1000, 1750));
            }
        }
    }
}
