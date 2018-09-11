package scripts.VorkathAnnihilator.Utils;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;

public class PotionUtil {

    public static int getDoses(String potionName) {

        for (int i= 4; i >= 1; i--) {
            if (Inventory.find(potionName + "(" + i +")").length>=1) {
                return i;
            }
        }
        return 0;
    }
    public static int drinkPotion(String potionName) {
        int doses = 0;
        for (int i= 4; i >= 1; i--) {
            if (Inventory.find(potionName + "(" + i +")").length>=1) {
                doses = i;
                break;
            }
        }
        String potion = potionName+"(" + doses + ")";
        if (Inventory.find(potion).length>=1) {
            Inventory.find(potion)[0].click("Drink");
            General.sleep(200, 250);
            return doses;
        }
        return 0;
    }
}
