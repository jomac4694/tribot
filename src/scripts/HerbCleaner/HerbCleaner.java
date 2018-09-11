package scripts.HerbCleaner;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import scripts.HerbCleaner.data.Variables;


@ScriptManifest(category = "Money", authors = "Joe", name = "Herb Cleaner")
public class HerbCleaner extends Script {

    private enum State {
        CLEANING, BANKING, DEFAULT
    }

    @Override
    public void run() {

        while (true) {
            switch (getState()) {
                case CLEANING:
                    cleanHerb(Variables.GRIMY_RANARR);
                    break;
                case BANKING:
                    General.sleep(1000, 5000);
                    break;
                case DEFAULT:
                    General.sleep(100, 500);
                    break;

            }

        }
    }


    public void cleanHerb(int herbID) {
        if (Inventory.getCount(herbID) >= 1) {
            if (Inventory.find(herbID)[0] != null) {
                Inventory.find(herbID)[0].click("Clean");
                General.sleep(500, 750);
            }
        }
    }


    public State getState() {
        if (Inventory.getCount(Variables.GRIMY_RANARR) >= 1) {
            return State.CLEANING;
        }
        else if (Inventory.getCount(Variables.GRIMY_RANARR) < 1) {
            return State.BANKING;
        }
        else {
            return State.DEFAULT;
        }
    }

}
