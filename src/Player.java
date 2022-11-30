/**
 * Player class contain attributes and methods that specific to a player object in the game
 *
 * @author Hogi Kwak
 */

import bagel.*;
import bagel.util.Point;
import bagel.util.Colour;

public class Player extends Character {

    private final Image PLAYER_LEFT = new Image("res/fae/faeLeft.png");
    private final Image PLAYER_RIGHT = new Image("res/fae/faeRight.png");
    private final Image PLAYER_ATK_LEFT = new Image("res/fae/faeAttackLeft.png");
    private final Image PLAYER_ATK_RIGHT = new Image("res/fae/faeAttackRight.png");
    private final static int DAMAGE = 20;
    private final static int MAX_HEALTH = 100;
    private final static double PLAYER_MOVE_SPEED = 2;
    private final static int HEALTH_X = 20;
    private final static int HEALTH_Y = 25;
    private final static Point HEALTH_BOTTOMLEFT = new Point(HEALTH_X, HEALTH_Y);
    private final Font FONT = new Font("res/frostbite.ttf", FONT_SIZE);
    private final static int FONT_SIZE = 30;
    private Point prevPosition;
    private int attackState = 0;
    private int attackCount = 0;
    private final static boolean IS_RIGHT = true;
    private final static int PLAYER_ATTACK_FRAME = 60;
    private final static int PLAYER_COOLDOWN_FRAME = 120;
    private static final String OBJECT_TYPE = "Fae";

    public Player(double x, double y) {
        super(x, y, OBJECT_TYPE, MAX_HEALTH, DAMAGE, PLAYER_MOVE_SPEED, FONT_SIZE
                , HEALTH_BOTTOMLEFT, IS_RIGHT);
        this.prevPosition = this.getPosition();
        this.drawObjectBox();
    }

    /**
     * if player ATTACK
     * @return boolean TRUE if player ATTACK and IDLE
     */
    public boolean isAttack() {
        return this.attackState == 1;
    }
    public boolean isReadyToAttack() {
        return this.attackState == 0;
    }

    /**
     * set player object ATTACK state
     * @param attackState boolean TRUE/FALSE to specify if player are entered/removed from ATTACK
     */
    public void setAttackState(int attackState) {
        this.attackState = attackState;
    }

    public void move(Input input) {
        this.prevPosition = this.getPosition();
        if (input.isDown(Keys.LEFT)) {
            this.getNewPosition(-this.getMoveSpeed(), 0);
            this.setIsRight(false);
        }
        if (input.isDown(Keys.RIGHT)) {
            this.getNewPosition(this.getMoveSpeed(), 0);
            this.setIsRight(true);
        }
        if (input.isDown(Keys.UP)) {
            this.getNewPosition(0, -this.getMoveSpeed());
        }
        if (input.isDown(Keys.DOWN)) {
            this.getNewPosition(0, this.getMoveSpeed());
        }
    }

    /**
     * revert Player to previous position
     */
    public void setPrevPosition() {
        this.setPosition(prevPosition);
    }


    /**
     * get player image
     * @return Image object based on the current behavior this player object
     */
    @Override
    protected Image getCurrentImage() {
        if (this.isAttack()) {
            if (this.isRight()) {
                return PLAYER_ATK_RIGHT;
            } else {
                return PLAYER_ATK_LEFT;
            }
        }
        else {
            if (this.isRight()) {
                return PLAYER_RIGHT;
            } else {
                return PLAYER_LEFT;
            }
        }
    }

}
