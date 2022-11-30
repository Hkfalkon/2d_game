/**
 * Character class (across all movable objects)
 *
 *  @author Hogi Kwak
 */

import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;

public abstract class Character extends ObjectEntity implements MoveEntity {
    private int health;
    private boolean isRight;
    private boolean isInvincible = false;
    private int counter = 0;
    private Point healthBottomLeft;
    private final int damage;
    private final double MOVE_SPEED;
    private final Font HEALTH_FONT;
    private final DrawOptions HEALTH_COLOR = new DrawOptions();
    private final int MAX_HEALTH;
    private final static int CHARACTER_INVINCIBLE_FRAME = 180;
    private final static int ORANGE_BOUNDARY = 65;
    private final static int RED_BOUNDARY = 35;
    private final static Colour GREEN = new Colour(0, 0.8, 0.2);
    private final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    private final static Colour RED = new Colour(1, 0, 0);
    private final static String SINKHOLE_OBJECT_TYPE = "SinkHole";

    public Character(double x, double y, String objectType, int maxHealth, int damage, double moveSpeed
            , int healthFontSize, Point healthBottomLeft, boolean isRight) {
        super(x, y, objectType);
        this.health = this.MAX_HEALTH = maxHealth;
        this.damage = damage;
        this.MOVE_SPEED = moveSpeed;
        this.HEALTH_FONT = new Font("res/frostbite.ttf", healthFontSize);
        this.healthBottomLeft = healthBottomLeft;
        this.isRight = isRight;
    }

    protected double getMoveSpeed() {
        return this.MOVE_SPEED;
    }

    protected boolean isRight() {
        return isRight;
    }

    protected int getDamage() {
        return damage;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    protected void setIsRight(boolean isRight) {
        this.isRight = isRight;
    }

    protected boolean isDead() {
        return this.health == 0;
    }

    protected boolean checkOutOfBound(Point topLeft, Point bottomRight) {
        return this.getPosition().x < topLeft.x || this.getPosition().x > bottomRight.x ||
                this.getPosition().y < topLeft.y || this.getPosition().y > bottomRight.y;
    }

    protected void receiveDamage(int damage, String attacker) {
        if (!isDead() & ((this.health - damage) > 0)) {
            this.health -= damage;
        } else {
            this.health = 0;
        }
        System.out.format("%s inflicts %d damage points on %s. %s’s current health: %d/%d\n",
                attacker, damage, this.getObjectType(), this.getObjectType(), this.health, MAX_HEALTH);
        if (!attacker.equals(SINKHOLE_OBJECT_TYPE)) {
            this.isInvincible = true;
        }
    }

    protected void updateInvincibleState() {
        if (this.isInvincible) {
            this.counter += 1;
            if (this.counter == CHARACTER_INVINCIBLE_FRAME) {
                this.isInvincible = false;
                this.counter = 0;
            }
        }
    }

    /**
     * initiate Character center point (for determining attack range)
     * @param obj any object that inherit GameObject that we want to find its center point
     * @return Point object which represent object image center point
     */
    protected <T extends ObjectEntity> Point getObjectCenter(T obj) {
        return new Point(obj.getPosition().x + (obj.getCurrentImage().getWidth() / 2),
                obj.getPosition().y + (obj.getCurrentImage().getHeight() / 2));
    }

    /**
     * if this object and other object is overlapping or not
     * @param obj any object that inherit ObjectEntity for overlapping
     * @return boolean TRUE if this object is overlap with another object, otherwise FALSE
     */
    protected <T extends ObjectEntity> boolean checkOverlap(T obj) {
        return this.getObjectBox().intersects(obj.getObjectBox());
    }

    protected void renderHealthBar(String objType) {
        int healthPercent = (int) Math.round(this.health * 100.0 / this.MAX_HEALTH);
        if (!objType.equals("Fae")) {
            this.healthBottomLeft = new Point(this.getPosition().x, this.getPosition().y - 6);
        }
        if (healthPercent >= ORANGE_BOUNDARY) {
            HEALTH_COLOR.setBlendColour(GREEN);
        } else if (healthPercent >= RED_BOUNDARY) {
            HEALTH_COLOR.setBlendColour(ORANGE);
        } else {
            HEALTH_COLOR.setBlendColour(RED);
        }
        HEALTH_FONT.drawString(String.format("%d%%", healthPercent), healthBottomLeft.x,
                healthBottomLeft.y, HEALTH_COLOR);
    }

    /**
     * rendering object on gameScreen
     */
    @Override
    public void renderObject() {
        super.renderObject();
        this.renderHealthBar(this.getObjectType());
    }

}
