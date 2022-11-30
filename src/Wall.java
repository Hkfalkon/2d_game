/** Wall class
 *
 * @author HOgi Kwak
 */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Wall extends ObjectEntity {
    private static final Image WALL_IMG = new Image("res/wall.png");
    private static final String OBJECT_TYPE = "Wall";

    public Wall(double x, double y) {
        super(x, y, OBJECT_TYPE);
        this.drawObjectBox();
    }

    @Override
    protected Image getCurrentImage() {
        return WALL_IMG;
    }

}