package scripts.VorkathAnnihilator.Utils;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import scripts.VorkathAnnihilator.Data.Variables;

public class FoodUtil {
    public static void eat() {
        if (Inventory.find(Variables.FOOD_ID).length >=1) {
            Inventory.find(Variables.FOOD_ID)[0].click("Eat");
            Variables.eatStartTime = System.currentTimeMillis();
        }
    }

    public static boolean lowHealth(int x) { return Skills.getCurrentLevel(Skills.SKILLS.HITPOINTS) < x; }
}
