package scripts.BloodRuneCrafter;


import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Skills;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;
import scripts.BloodRuneCrafter.Actions.*;
import scripts.BloodRuneCrafter.Data.Vars;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ScriptManifest(name = "BloodRune Crafter", category = "Runecrafting" , authors = "Joe")
public class BloodCrafter extends Script implements Painting {
    @Override
    public void run() {
        ABCUtil abc = new ABCUtil();
        Vars.lastWorldHop = System.currentTimeMillis();
        Vars.nextHop = 30000 + General.random(-2000, 2000);
        Mouse.setSpeed(200);
        General.useAntiBanCompliance(true);
        List<Action> actions = new ArrayList<>();
        Vars.startingXp = Skills.SKILLS.RUNECRAFTING.getXP();
        Collections.addAll(actions, new MineEss(abc), new HandleFuckUp(), new VenerateEss(), new WalkToMine(), new BindRunes(), new AltarToMine());
        Vars.startTime = System.currentTimeMillis();
        while (true) {
            for (Action a : actions) {
                if (a.active()) {
                    a.activate();
                }
                General.sleep(300, 500);
            }
        }
    }
        private String time(long time) {
            long seconds = time /1000;
            long minutes = time / 1000 / 60;
            long hours = time / 1000 / 60 / 60;
            return hours % 24 + " : " + minutes % 60 + " : " + seconds % 60;
        }


        @Override
        public void onPaint(Graphics graphics) {
            Vars.xpGained = Skills.SKILLS.RUNECRAFTING.getXP() - Vars.startingXp;
            Vars.runTime = System.currentTimeMillis() - Vars.startTime;
            graphics.setColor(new Color(0, 0, 255, 110));
            graphics.fillRect(35, 45, 320, 80);
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Century Gothix", Font.BOLD, 12));
            graphics.drawString("Runtime: " + time(Vars.runTime), 50, 60);
            graphics.drawString("Script State: " + Vars.CURRENT_STATE, 50, 75);
            graphics.drawString("Exp Gained: " + Vars.xpGained + " (" + (int) (Vars.xpGained * ((double) 3600000/Vars.runTime)) + ")", 50, 90);
            graphics.drawString("Exp to Level: " + Skills.getXPToLevel(Skills.SKILLS.RUNECRAFTING, Skills.getActualLevel(Skills.SKILLS.RUNECRAFTING) + 1), 50, 105);
        }
}
