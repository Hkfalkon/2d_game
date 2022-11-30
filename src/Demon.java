/**
 * Demon class
 *
 * @author HOgi Kwak
 */

import java.util.concurrent.ThreadLocalRandom;

public class Demon extends Enemy {

    private final static String LEFT_IMG = "res/demon/demonLeft.png";
    private final static String RIGHT_IMG = "res/demon/demonRight.png";
    private final static String LEFT_INVINCIBLE_IMG = "res/demon/demonInvincibleLeft.png";
    private final static String RIGHT_INVINCIBLE_IMG = "res/demon/demonInvincibleRight.png";
    private final static int DAMAGE = 10;
    private final static int ATK_RANGE = 150;
    private final static int MAX_HEALTH = 40;
    private final static String OBJECT_TYPE = "Demon";

    public Demon(double x, double y) {
        super(x, y, OBJECT_TYPE, MAX_HEALTH, DAMAGE, ATK_RANGE, ThreadLocalRandom.current().nextInt(2),
                LEFT_IMG, RIGHT_IMG, LEFT_INVINCIBLE_IMG, RIGHT_INVINCIBLE_IMG);
    }

}
