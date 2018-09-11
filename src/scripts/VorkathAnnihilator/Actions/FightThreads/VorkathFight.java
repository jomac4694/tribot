package scripts.VorkathAnnihilator.Actions.FightThreads;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.VorkathAnnihilator.Actions.Action;
import scripts.VorkathAnnihilator.Data.Variables;
import scripts.VorkathAnnihilator.Utils.*;
import scripts.VorkathAnnihilator.VorkathInstance;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class VorkathFight extends Action implements Runnable, MouseListener {
    private long antiVenomStartTime;
    private long antiFireStartTime;
    private long atkStartTime;
    private long freezePhaseStart;
    private boolean freezePhase = false;

    @Override
    public void run() {
        if (!active() && Variables.t3.isInterrupted()) {
            General.sleep(100, 250);
        }
        else {
            while (true) {
                if (!active() || Variables.t3.isInterrupted()) {
                    General.println("Fight thread sleeping");
                    General.sleep(250, 350);
                    continue;
                }
                else {
                    General.println("activating fight thread.");
                    activate();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        General.println("MOUSE CLICKED MOTHEFUKER");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private enum ScriptState {  LEAVE, LOOTING, KILLING, DEFAULT, POKE}
    private enum FightState {ATTACKING, ATTACK, FREEZE_PHASE_START, SLIME_WALKING, WAKING, DODGING, EAT, FROZEN, DEFAULT }

    @Override
    public boolean active() {
        return !Variables.slimePhase && !droppingBomb() && (NPCs.findNearest(Variables.SLEEPING_VORKATH).length >= 1 || NPCs.findNearest(Variables.ATTACKING_VORKATH).length >= 1);
    }

    public VorkathFight(ACamera camera) {
        super(camera);
        freezePhaseStart = 0;
        Variables.eatStartTime = 0;
        atkStartTime = 0;
        Variables.bombRunStartTime = 0;
        antiFireStartTime = 0;
        antiVenomStartTime = 0;
        Variables.vorkinstance = null;
    }
    @Override
    public void activate() {
        if (Variables.vorkinstance == null) {
            if (Objects.findNearest(15, 31822).length>=1) {
                RSTile t = Objects.findNearest(15, 31822)[0].getPosition();
                Variables.vorkinstance = new VorkathInstance(new RSTile(t.getX(), t.getY()+1,0));
            }
        }

        switch (getScriptState()) {
            case LOOTING:
                Variables.setState("LOOTING");
                VorkathUtil.deactivateQuickPrayer();
                LootingUtil.loot();
                break;
            case LEAVE:
                Variables.setState("LEAVING VORKATH");
                beOutty();
                Variables.vorkinstance = null;
                General.sleep(1500,2250);
                break;
            case POKE:
                if (!cameraSet()) {
                    camera.setCameraAngle(88);
                    camera.setCameraRotation(22);
                }
                if (Variables.TESTING.equals("no")) {
                    potUpStart();
                    bolts();
                }
                poke();
                Variables.vorkinstance.getFightTile().click("Walk here");
                General.sleep(1500);
                break;
            case KILLING:
                if (!GameTab.getOpen().equals(GameTab.TABS.INVENTORY)) {
                    Keyboard.pressFunctionKey(1);
                }
                if (spawnDying()) {
                    freezePhase = false;
                    Variables.setState("SPAWN DYING");
                    attack();
                }
                if (NPCs.findNearest(Variables.ATTACKING_VORKATH).length >= 1) {
                    if (NPCs.findNearest(Variables.ATTACKING_VORKATH)[0].getHealthPercent() <= 0) {
                        VorkathUtil.deactivateQuickPrayer();
                        General.sleep(4500, 5500);
                    }
                }
                VorkathUtil.enableRun();
                long freezeTime = System.currentTimeMillis() - freezePhaseStart;
                if (freezeTime > 4600) {
                    freezePhase = false;
                }

                if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MISSILES)
                        && !freezePhase && !Variables.slimePhase
                        && NPCs.findNearest(Variables.ATTACKING_VORKATH).length >= 1
                        && !spawnOnScreen()
                        && !droppingBomb() || spawnDying() && !Variables.droppingBomb) {
                    VorkathUtil.activateQuickPrayer();
                }
                if (Skills.getCurrentLevel(Skills.SKILLS.PRAYER) < 30 && !freezePhase && !Variables.slimePhase && !droppingBomb()
                        && !spawnOnScreen()) {
                    PotionUtil.drinkPotion("Prayer potion");
                }


                switch (getFightState()) {
                    case EAT:
                        Variables.setState("EATING");
                        FoodUtil.eat();
                        break;

                    case FREEZE_PHASE_START:
                        freezePhaseSetup();
                        break;
                    case WAKING:
                        Variables.setState("VORK WAKING");
                        if (!cameraSet()) {
                            camera.setCameraAngle(88);
                            camera.setCameraRotation(22);
                        }

                        Variables.vorkinstance.getFightTile().click("Walk here");
                        General.sleep(1500);

                        break;
                    case ATTACK:
                        if (Variables.TESTING.equals("no")) {
                            potUp();
                            bolts();
                        }
                        attack();
                        break;
                    // case DODGING:
                    //     dodge();
                    //     break;

                   // case SLIME_WALKING:
                    //    Variables.setState("SLIME WALKING");
                     //   Variables.slimePhaseStart = System.currentTimeMillis();
                    //    disableRun();
                      //  slimeWalk();
                      //  break;

                    case FROZEN:
                        Variables.setState("FROZEENNN");
                        killSpawn();
                        break;
                    case ATTACKING:
                        Variables.setState("DOING NOTHING ATK");
                        General.sleep(400);
                        break;
                    case DEFAULT:
                        Variables.setState("DOING NOTHING DEFAULT");
                        General.sleep(100, 150);
                        break;

                }
                break;

        }
    }


    public void potUpStart() {
        long antiFTime = System.currentTimeMillis() - antiFireStartTime;
        long antiVTime = System.currentTimeMillis() - antiVenomStartTime;
        if (Skills.getCurrentLevel(Skills.SKILLS.RANGED) <= 108) {
            PotionUtil.drinkPotion(Variables.RANGING_POT);
            General.sleep(1600);
        }
        if (antiFTime > 300000) {
            Variables.antiFExpected = PotionUtil.drinkPotion(Variables.ANTI_FIRE) - 1;
            antiFireStartTime = System.currentTimeMillis();
            General.sleep(1600);

        }
        if (antiVTime > 210000) {
            Variables.antiVExpected = PotionUtil.drinkPotion(Variables.ANTI_VENOM) - 1;
            antiVenomStartTime = System.currentTimeMillis();
            General.sleep(1600);
        }

    }

    public boolean spawnDying() {
        RSNPC[] cralwer = NPCs.findNearest(Variables.CRAWLER);
        if (cralwer.length>=1) {
            if (cralwer[0] != null) {
                if (NPCs.findNearest(Variables.CRAWLER)[0].getHealthPercent() <= 0) {
                    return true;
                }
            }
        }
        return false;
    }
    public void potUp() {

        int antiFActual = PotionUtil.getDoses(Variables.ANTI_FIRE);
        int antiVActual = PotionUtil.getDoses(Variables.ANTI_VENOM);
        long antiFTime = System.currentTimeMillis() - antiFireStartTime;
        long antiVTime = System.currentTimeMillis() - antiVenomStartTime;
        if (!inDanger()) {
            if (Skills.getCurrentLevel(Skills.SKILLS.RANGED) <= 108) {
                PotionUtil.drinkPotion(Variables.RANGING_POT);
            }
            if (antiFTime > 300000 || antiFActual != Variables.antiFExpected) {
                Variables.antiFExpected = PotionUtil.drinkPotion(Variables.ANTI_FIRE) - 1;
                antiFireStartTime = System.currentTimeMillis();

            }
            if (antiVTime > 210000 || antiVActual != Variables.antiVExpected) {
                Variables.antiVExpected = PotionUtil.drinkPotion(Variables.ANTI_VENOM) - 1;
                antiVenomStartTime = System.currentTimeMillis();
            }
        }

    }
    public void bolts() {
        RSNPC[] vork = NPCs.find(Variables.ATTACKING_VORKATH);
        if (vork.length >= 1 && !droppingBomb() && !Variables.slimePhase && !freezePhase && !spawnOnScreen()) {
            if (vork[0].isInCombat()) {
                if (vork[0].getHealthPercent() <= 1.5) {
                    if (Inventory.find(21946).length >= 1) {
                        Inventory.find(21946)[0].click("Wield");
                        General.sleep(200);
                    }
                }
            }
        }
        if (NPCs.find(Variables.SLEEPING_VORKATH).length >= 1 && !freezePhase && !Variables.slimePhase && !spawnOnScreen()) {

            if (Inventory.find(21944).length >=1) {
                Inventory.find(21944)[0].click("Wield");
                General.sleep(200);
            }

        }
    }






    public boolean cameraSet() {

        return (Camera.getCameraAngle() > 80 &&
                Camera.getCameraAngle() < 94) && (Camera.getCameraRotation() < 30 &&
                Camera.getCameraRotation() > 18);
    }




    public boolean inDanger() {
        if (Variables.droppingBomb)
            return true;
        else
            return false;
    }

    public boolean poke() {
        RSNPC[] vork = NPCs.findNearest("Vorkath");
        if (vork.length >= 1) {
            vork[0].click("Poke");
            General.sleep(1500);
            VorkathUtil.activateQuickPrayer();
            return true;
        }
        return false;
    }
    public void attack() {
        Variables.setState("ATTACKING");
        long timeSinceAtk = System.currentTimeMillis() - atkStartTime;
        if (!cameraSet()) {
            camera.setCameraAngle(88);
            camera.setCameraRotation(22);
        }
        if (!Variables.vorkinstance.getArea().contains(Player.getPosition()) && !Player.isMoving()) {
            Variables.vorkinstance.getFightTile().click("Walk here");
            General.sleep(300, 500);
        }
        RSNPC[] vork = NPCs.findNearest(Variables.ATTACKING_VORKATH);
        if (vork.length >=1 && timeSinceAtk >= 2300) {
            if (vork[0].click("Attack")) {
                atkStartTime = System.currentTimeMillis();
            }
        }

    }
    public void freezePhaseSetup() {
        freezePhaseStart = System.currentTimeMillis();
        VorkathUtil.deactivateQuickPrayer();
        RSNPC[] crawler = NPCs.findNearest("Zombified Spawn");
        if (crawler.length < 1) {
            camera.setCameraRotation(0);
            RSProjectile[] spawn = Projectiles.getAll();
            Player.getPosition().click("Walk here");
            Player.getPosition().click("Walk here");
            Player.getPosition().click("Walk here");

            if (!Magic.isSpellSelected()) {
                org.tribot.api2007.Magic.selectSpell("Crumble Undead");
            }
        }
    }
    public boolean spawnOnScreen() {
        RSNPC[] crawler = NPCs.findNearest("Zombified Spawn");
        if (crawler.length >=1) {
            freezePhase = true;
            return true;

        }
        return false;
    }

    public ScriptState getScriptState() {
        if (LootingUtil.looting()) {
            return ScriptState.LOOTING;
        }
        else if (NPCs.find(Variables.SLEEPING_VORKATH).length >= 1 && Inventory.getCount(Variables.FOOD_ID) < 8 && !LootingUtil.looting()) {
            return ScriptState.LEAVE;
        }
        else if (NPCs.findNearest(Variables.SLEEPING_VORKATH).length >= 1 && !LootingUtil.looting()) {
            return ScriptState.POKE;
        }
        else if (NPCs.find(Variables.ATTACKING_VORKATH).length>=1 && !LootingUtil.looting()){
            return ScriptState.KILLING;
        }
        else {
            return ScriptState.DEFAULT;
        }
    }

    public FightState getFightState() {
        long timeSinceEat = System.currentTimeMillis() - Variables.eatStartTime;

        //if (Variables.slimePhase) {
         //   return FightState.SLIME_WALKING;
       // }  else
         if (freezePhase) {
            return FightState.FROZEN;
        }
        else if (freezePhaseStarting()) {
            return FightState.FREEZE_PHASE_START;
        }
        else if (FoodUtil.lowHealth(62) && !inDanger() && timeSinceEat >= 1500) {
            return FightState.EAT;
        }

        else if (Variables.droppingBomb) {
            return FightState.DODGING;
        }
        else if (!Variables.slimePhase && !freezePhase
                && Player.getAnimation() != 7552
                && NPCs.findNearest(Variables.ATTACKING_VORKATH).length >= 1 &&
                NPCs.findNearest(Variables.ATTACKING_VORKATH)[0].isInteractingWithMe()
                && !inDanger()) {
            return FightState.ATTACK;
        }
        else if (NPCs.findNearest(Variables.WAKING_VORKATH).length >= 1) {
            if (NPCs.findNearest(Variables.WAKING_VORKATH)[0].getAnimation() == 7950) {
                return FightState.WAKING;
            }
        }
        else {
            return FightState.ATTACKING;
        }
        return FightState.DEFAULT;

    }


    public void beOutty() {
        if (Magic.selectSpell("Teleport to House")) {
            Timing.waitCondition(new Condition() {

                @Override
                public boolean active() {
                    return Player.getAnimation() != 714;
                }
            }, General.random(2000, 2750));
        }
    }

    public void killSpawn() {
        if (freezePhase) {
            /*
            RSProjectile[] pro = Projectiles.getAll();
            if (pro.length >0) {
                if (pro[0].getGraphicID() == 1484) {
                    RSTile t = new RSTile(pro[0].getStartX()+(int) pro[0].getSpeedX(),pro[0].getStartY() +(int) pro[0].getSpeedY(), 0);
                    Camera.turnToTile(t);
                }
            }
            */
            RSNPC[] crawler = NPCs.findNearest("Zombified Spawn");
            if (crawler.length >= 1 && crawler[0].getHealthPercent() > .1) {
                Variables.setState("KILLING SPAWN");
                if (!crawler[0].isOnScreen())
                    camera.setCameraRotation(44);
                if (DynamicClicking.clickRSNPC(crawler[0], "Cast")) {
                    freezePhase = false;
                }
                if (Player.getAnimation() == 724) {
                    freezePhase = false;
                }
            }
            if (FoodUtil.lowHealth(51)) {
                FoodUtil.eat();
            }
            if (spawnDying()) {
                freezePhase = false;
            }
        }
    }
    public boolean freezePhaseStarting() {
        RSProjectile[] projects = Projectiles.getAll();
        for (RSProjectile p : projects) {
            if (p.getGraphicID() == 395 || p.getGraphicID() == Variables.CRAWLER_PROJECTILE) {
                Variables.setState("FREEZE PHASE STARTING");
                freezePhase = true;
                return true;
            }
        }
        return false;
    }

    public boolean droppingBomb() {
        RSProjectile[] projects = Projectiles.getAll();
        for (RSProjectile p : projects) {
            if (p.getGraphicID() == 1481) {
                Variables.droppingBomb = true;
                return true;
            }
        }
        return false;
    }

}
