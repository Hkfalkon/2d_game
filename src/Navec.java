/**
 * Navec class
 *
 * @author HOgi kwak
 */

public class Navec extends Enemy{

    private final static String LEFT_IMG = "res/navec/navecLeft.png";
    private final static String RIGHT_IMG = "res/navec/navecRight.png";
    private final static String LEFT_INV_IMG = "res/navec/navecInvincibleLeft.png";
    private final static String RIGHT_INV_IMG = "res/navec/navecInvincibleRight.png";
    private final static int ENEMY_TYPE = 1;
    private final static int DAMAGE = 20;
    private final static int ATK_RANGE = 200;
    private final static int MAX_HEALTH = 80;
    private final static String OBJECT_TYPE = "Navec";

    public Navec(double x, double y) {
        super(x, y, OBJECT_TYPE, MAX_HEALTH, DAMAGE, ATK_RANGE, ENEMY_TYPE, LEFT_IMG, RIGHT_IMG,
                LEFT_INV_IMG, RIGHT_INV_IMG);
    }

}
