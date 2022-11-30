

import bagel.util.Point;

public interface MoveEntity {

    Point getPosition();

    void setPosition(Point position);

    void drawObjectBox();

    /**
     * assign new position to the object & draw new objectBox(Rectangle Class) for checking object overlap
     * @param moveSizeX move size to get position in x-axis
     * @param moveSizeY move size to get position in y-axis
     */
    default void getNewPosition(double moveSizeX, double moveSizeY) {
        double positionX = this.getPosition().x + moveSizeX;
        double positionY = this.getPosition().y + moveSizeY;
        this.setPosition(new Point(positionX, positionY));
        this.drawObjectBox();
    }

}
