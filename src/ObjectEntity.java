/**
 * GameObject class (attribute, method for all objects
 *
 * @author Hogi Kwak
 */

import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class ObjectEntity {
    private Point position;
    private Rectangle objectBox;
    protected String objectType;
    private boolean isActive;

    public ObjectEntity(double x, double y, String objectType) {
        this.position = new Point(x, y);
        this.objectType = objectType;
        this.isActive = true;
    }

    public ObjectEntity(double x, double y, String objectType, boolean isActive) {
        this.position = new Point(x, y);
        this.objectType = objectType;
        this.isActive = isActive;
    }

    /**
     * get object current position
     * @return Point image topleft point in the game screen
     */
    public Point getPosition() {
        return position;
    }

    protected Rectangle getObjectBox() {
        return objectBox;
    }

    protected String getObjectType() {
        return objectType;
    }

    protected boolean isActive() {
        return isActive;
    }

    /**
     * assign object new position
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    protected void setActive(boolean active) {
        isActive = active;
    }

    protected void renderObject() {
        this.getCurrentImage().drawFromTopLeft(this.position.x, this.position.y);
    }

    protected void renderObject(DrawOptions option) {
        this.getCurrentImage().drawFromTopLeft(this.position.x, this.position.y);
    }


    /**
     * assign new object Rectangle based on its current position
     */
    public void drawObjectBox() {
        double imgWidth = this.getCurrentImage().getWidth();
        double imgHeight = this.getCurrentImage().getHeight();
        this.objectBox = new Rectangle(this.position, imgWidth, imgHeight);
    }

    protected abstract Image getCurrentImage();

}
