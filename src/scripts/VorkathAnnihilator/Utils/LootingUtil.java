package scripts.VorkathAnnihilator.Utils;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSNPC;
import scripts.VorkathAnnihilator.Data.Variables;

public class LootingUtil {

    public static void loot() {
        int emptySlots = 28 - Inventory.getAll().length;
        RSGroundItem[] items = GroundItems.findNearest(Filters.GroundItems.idNotEquals(Variables.BLUE_DHIDE, Variables.FOOD_ID));
        int numGroundItems = items.length;

        if (items.length >= 1) {
            if (emptySlots < numGroundItems) {
                int toDrop = numGroundItems - emptySlots;
                for (int i = 0; i < toDrop; i++) {

                    Inventory.find(385)[0].click("Drop");
                    General.sleep(500);
                }
            }
            if (items.length >= 1) {
                RSItemDefinition def = items[0].getDefinition();
                if (def != null) {
                    String name = def.getName();
                    if (name != null) {
                        int id = items[0].getID();
                        int count = Inventory.getCount(id);
                        if (def.isNoted()) {
                            Variables.profit += Variables.getPrice(items[0].getID() - 1, items[0].getStack());
                        }
                        else {
                            Variables.profit += Variables.getPrice(items[0].getID(), items[0].getStack());
                        }
                        if (items[0].click("Take " + name)) {
                            Timing.waitCondition(new Condition() {

                                @Override
                                public boolean active() {
                                    return Inventory.getCount(id) > count;
                                }
                            }, General.random(1000, 2000));
                        }
                    }
                }
            }
            General.sleep(500);
        }
        if (!looting()) {
            RSGroundItem[] sharks = GroundItems.findNearest(Variables.FOOD_ID);
            if (sharks.length >= 1) {
                if (!Inventory.isFull()) {
                    emptySlots = 28 - Inventory.getAll().length;
                    for (int i = 0; i < emptySlots; i++) {
                        sharks[0].click("Take");
                        General.sleep(599);
                    }
                }
            }
        }
    }
    public static boolean looting() {
        RSNPC[] vork = NPCs.findNearest(Variables.SLEEPING_VORKATH);
        RSGroundItem[] items = GroundItems.findNearest(Filters.GroundItems.idNotEquals(Variables.BLUE_DHIDE, Variables.FOOD_ID));
        if (vork.length >= 1 && items.length >=1 ) {
            return true;
        }
        return false;
    }

}
