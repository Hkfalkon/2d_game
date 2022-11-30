/**
 * Tree class
 *
 * @author Hogi Kwak
 */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Tree extends ObjectEntity {
    private static final Image TREE_IMG = new Image("res/tree.png");
    private static final String OBJECT_TYPE = "Tree";

    public Tree(double x, double y) {
        super(x, y, OBJECT_TYPE);
        this.drawObjectBox();
    }

    @Override
    protected Image getCurrentImage() {
        return TREE_IMG;
    }

}
