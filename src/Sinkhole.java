/**
 * SinkHole class contain attributes and methods that specific to a SinkHole object in the game
 * SWEN20003 Project 2B, Semester 2, 2022
 *
 * @author Hogi Kwak
 */

import bagel.Image;

public class Sinkhole extends ObjectEntity {
    private static final Image SINKHOLE_IMG = new Image("res/sinkhole.png");
    private static final int DAMAGE_POINTS = 30;
    private static final String OBJECT_TYPE = "SinkHole";

    public Sinkhole(double x, double y) {
        super(x, y, OBJECT_TYPE);
        this.drawObjectBox();
    }

    /**
     * get SinkHole object damage
     * @return int damage to player
     */
    public static int getSinkHoleDmg() {
        return DAMAGE_POINTS;
    }

    @Override
    protected Image getCurrentImage() {
        return SINKHOLE_IMG;
    }

}