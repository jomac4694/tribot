package scripts.BloodRuneCrafter.Data;

public class Vars {

public static String CURRENT_STATE = "Script Started";
public static String PREVIOUS_STATE  = CURRENT_STATE;
public static long startTime, runTime;
public static int startingXp, xpPerHr, xpGained;
public static long lastWorldHop, nextHop;


public static void setState(String state) {
    PREVIOUS_STATE = CURRENT_STATE;
    CURRENT_STATE = state;
}
}
