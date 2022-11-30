/**
 * Enemy class of all Enemy objects in the game (Demon and Navec)
 *
 * @UTHOR Hogi kwak
 */

import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Enemy extends Character {
    private final int enemyType;
    /**
     * enemy movement direction :
     *  0 = down
     *  1 = up
     *  2 = left
     *  3 = right
     */
    private int moveDirection;
    private final Image LEFT_IMG;
    private final Image RIGHT_IMG;
    private final Image LEFT_INV_IMG;
    private final Image RIGHT_INV_IMG;
    private final int ATK_RANGE;
    private static final double TIME_SCALE_POS = 1.5;
    private static final double TIME_SCALE_NEG = 0.5;
    private static final int ENEMY_HEALTH_FONT_SIZE = 15;
    private static final double TOP_LEFT_FIRE_RADIAN = 0;
    private static final double BOTTOM_LEFT_FIRE_RADIAN = (-90 * (Math.PI) / 180);
    private static final double TOP_RIGHT_FIRE_RADIAN = (90 * (Math.PI) / 180);
    private static final double BOTTOM_RIGHT_FIRE_RADIAN = (180 * (Math.PI) / 180);
    private boolean isInvincible = false;
    private int health;
    private Point healthBottomLeft;
    private final Font HEALTH_FONT;
    private final DrawOptions HEALTH_COLOR = new DrawOptions();
    private final int MAX_HEALTH;
    private int counter = 0;
    private final static int ORANGE_BOUNDARY = 65;
    private final static int RED_BOUNDARY = 35;
    private final static Colour GREEN = new Colour(0, 0.8, 0.2);
    private final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    private final static Colour RED = new Colour(1, 0, 0);
    private final static String SINKHOLE_TYPE = "SinkHole";
    private final static int CHARACTER_INVINCIBLE_FRAME = 180;
    private Fire fireObject;
    private final DrawOptions IMG_ROTATION = new DrawOptions();



    public Enemy(double x, double y, String objType, int maxHealth, int damage, int atkRange, int enemyType,
                 String leftImage, String rightImage, String leftInvincibleImage, String rightInvincibleImage) {
        super(x, y, objType, maxHealth, damage, ThreadLocalRandom.current().nextDouble(0.2, 0.7),
                ENEMY_HEALTH_FONT_SIZE, new Point(x, y - 6), ThreadLocalRandom.current().nextBoolean());
//        super(x, y, objType);
        this.enemyType = enemyType;
        this.RIGHT_IMG = new Image(rightImage);
        this.LEFT_IMG = new Image(leftImage);
        this.RIGHT_INV_IMG = new Image(rightInvincibleImage);
        this.LEFT_INV_IMG = new Image(leftInvincibleImage);
        this.health = this.MAX_HEALTH = maxHealth;
        this.HEALTH_FONT = new Font("res/frostbite.ttf", ENEMY_HEALTH_FONT_SIZE);
        this.healthBottomLeft = healthBottomLeft;
        this.fireObject = new Fire(0, 0, this.getObjectType());
        this.ATK_RANGE = atkRange;
        this.drawObjectBox();
    }


    public void move(int timeScale) {
        // enemy aggressive
        if (this.enemyType == 1) {
            double baseNumber;
            if (timeScale >= 0) {
                baseNumber = TIME_SCALE_POS;
            } else {
                baseNumber = TIME_SCALE_NEG;
            }
            double moveSize = this.getMoveSpeed() * Math.pow(baseNumber, Math.abs(timeScale));
            switch (this.moveDirection) {
                // down
                case 0:
                    this.getNewPosition(0, moveSize);
                    break;
                // up
                case 1:
                    this.getNewPosition(0, -moveSize);
                    break;
                // left
                case 2:
                    this.getNewPosition(-moveSize, 0);
                    this.setIsRight(false);
                    break;
                // right
                case 3:
                    this.getNewPosition(moveSize, 0);
                    this.setIsRight(true);
                    break;
            }
        }
    }

    /**
     * reverse enemy object movement to the opposite direction
     */
    public void reverseMove() {
        switch (this.moveDirection){
            case 0:
                this.moveDirection = 1;
                break;
            case 1:
                this.moveDirection = 0;
                break;
            case 2:
                this.moveDirection = 3;
                break;
            case 3:
                this.moveDirection = 2;
                break;
        }
    }

    @Override
    protected Image getCurrentImage() {
        if (this.isInvincible()) {
            if (this.isRight()) {
                return this.RIGHT_INV_IMG;
            } else {
                return this.LEFT_INV_IMG;
            }
        } else {
            if (this.isRight()) {
                return this.RIGHT_IMG;
            } else {
                return this.LEFT_IMG;
            }
        }
    }

    protected boolean isDead() {
        return this.health == 0;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * inflict damage to character object and produce a log to console
     * @param damage amount of damage (int) to inflict to this character
     * @param attacker type of object damaging to this character
     */
    protected void receiveDamage(int damage, String attacker) {
        if (!isDead() & ((this.health - damage) > 0)) {
            this.health -= damage;
        } else {
            this.health = 0;
        }
        System.out.format("%s inflicts %d damage points on %s. %s’s current health: %d/%d\n",
                attacker, damage, this.getObjectType(), this.getObjectType(), this.health, MAX_HEALTH);
        if (!attacker.equals(SINKHOLE_TYPE)) {
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

    @Override
    public void renderObject() {
        super.renderObject();
        this.renderHealthBar(this.getObjectType());
    }

    public void attack(Player player) {
        Point enemyCentre = getObjectCenter(this);
        Point playerCentre = getObjectCenter(player);
        if (enemyCentre.distanceTo(playerCentre) <= this.ATK_RANGE) {
            this.fireObject.setPosition(this.getPosition());
            if (player.getPosition().x <= this.getPosition().x & player.getPosition().y <= this.getPosition().y) {
                // draw Fire from top-left
                this.fireObject.getNewPosition(-this.fireObject.getFireWidth(), -this.fireObject.getFireHeight());
                this.IMG_ROTATION.setRotation(TOP_LEFT_FIRE_RADIAN);
            } else if (player.getPosition().x <= this.getPosition().x & player.getPosition().y > this.getPosition().y) {
                // draw Fire from bottom-left
                this.fireObject.getNewPosition(-this.fireObject.getFireWidth(), this.getCurrentImage().getHeight());
                this.IMG_ROTATION.setRotation(BOTTOM_LEFT_FIRE_RADIAN);
            } else if (player.getPosition().x > this.getPosition().x & player.getPosition().y <= this.getPosition().y) {
                // draw Fire from top-right
                this.fireObject.getNewPosition(this.getCurrentImage().getWidth(), -this.fireObject.getFireHeight());
                this.IMG_ROTATION.setRotation(TOP_RIGHT_FIRE_RADIAN);
            } else if (player.getPosition().x > this.getPosition().x & player.getPosition().y > this.getPosition().y) {
                // draw Fire from bottom-right
                this.fireObject.getNewPosition(this.getCurrentImage().getWidth(), this.getCurrentImage().getHeight());
                this.IMG_ROTATION.setRotation(BOTTOM_RIGHT_FIRE_RADIAN);
            }
            this.fireObject.renderObject(this.IMG_ROTATION);
            this.fireObject.setActive(true);
        }
        else {
            this.fireObject.setActive(false);
        }
    }

    protected Fire getFireObject() {
        return this.fireObject;
    }

}
