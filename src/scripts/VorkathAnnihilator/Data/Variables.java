package scripts.VorkathAnnihilator.Data;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.VorkathAnnihilator.Actions.FightThreads.SlimeThread;
import scripts.VorkathAnnihilator.VorkathInstance;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Variables {

    public static String PREVIOUS_SCRIPT_STATE = "SCRIPT STARTED";
    public static String CURRENT_SCRIPT_STATE = "SCRIPT STARTED";

    public static VorkathInstance vorkinstance;


    public static long bombRunStartTime;

    //config varibles
    public static String TESTING;

    //Invetory Item ID's and such
    public static int FOOD_ID;
    public static final String PRAY_POT = "Prayer potion";
    public static final String ANTI_VENOM = "Anti-venom+";
    public static final String RANGING_POT = "Ranging potion";
    public static final String ANTI_FIRE = "Extended super antifire";


    //vorkath stuff
    public static final int ATTACKING_VORKATH = 8061;
    public static final int SLEEPING_VORKATH = 8059;
    public static final int WAKING_VORKATH = 8058;
    public static final int CRAWLER_PROJECTILE = 1484;
    public static final int BLUE_DHIDE = 1751;
    public static final int CRAWLER = 8063;
    public static int profit = 0;
    public static int vorkhealth = 0;


    //potions
    public static int antiFExpected;
    public static int antiVExpected;

    //booleans
    public static boolean safeFromBomb = true;
    public static boolean slimePhase = false;
    public static boolean droppingBomb = false;


    //Timer variables
    public static long slimePhaseStart;
    public static long eatStartTime;
    public static long startTime;

    //others
    public static long moneyPerHr = 0;
    public static long timePassed;
    public static RSTile expectedTile = Player.getPosition();
    public static RSTile bombTile = Player.getPosition();


    //threads
    public static Thread t;
    public static SlimeThread t2;
    public static Thread t3;


    public static void setState(String state) {
        PREVIOUS_SCRIPT_STATE = CURRENT_SCRIPT_STATE;
        CURRENT_SCRIPT_STATE = state;
    }


    public static int getPrice(final int id, final int amount) {
        try {
            URL url = new URL("http://api.rsbuddy.com/grandExchange?a=guidePrice&i=" + id);
            URLConnection con = url.openConnection();
            con.setUseCaches(true);
            BufferedReader br = new BufferedReader(new
                    InputStreamReader(con.getInputStream()));
            String[] data = br.readLine().replace("{","").replace("}","").split(",");
            return Integer.parseInt(data[0].split(":")[1])*amount;
        } catch(Exception e) {


        }
        return -1;
    }
}
