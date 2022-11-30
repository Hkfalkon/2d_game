/**
 * Fire class
 *
 * @author Hogi Kwak
 */

import bagel.Image;

public class Fire extends ObjectEntity  implements MoveEntity {
    private final static Image DEMON_FIRE = new Image("res/demon/demonFire.png");
    private final static Image NAVEC_FIRE = new Image("res/navec/navecFire.png");
    private Image FIRE_IMG;
    private final double FIRE_WIDTH;
    private final double FIRE_HEIGHT;
    private final static String OBJECT_TYPE = "Fire";
    private final static String NAVEC_TYPE = "Navec";
    private final static String DEMON_TYPE = "Demon";


    public Fire(double x, double y, String demonType) {
        super(x, y, OBJECT_TYPE, false);
        if (demonType.equals(NAVEC_TYPE)) {
            this.FIRE_IMG = NAVEC_FIRE;
        }
        if (demonType.equals(DEMON_TYPE)){
            this.FIRE_IMG = DEMON_FIRE;
        }
        this.FIRE_WIDTH = this.FIRE_IMG.getWidth();
        this.FIRE_HEIGHT = this.FIRE_IMG.getHeight();
        this.drawObjectBox();

    }

    public double getFireWidth() {
        return FIRE_WIDTH;
    }

    public double getFireHeight() {
        return FIRE_HEIGHT;
    }

    protected Image getCurrentImage() {
        return FIRE_IMG;
    }
}