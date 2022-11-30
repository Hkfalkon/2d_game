/**
 * Level1 class
 *
 * @author Hogi kwak
 */

import bagel.*;
import bagel.util.Point;

public class Level1 extends Level {
    // variable for controlling enemy movement speed
    private int timeScale = 0;
    private final static String LEVEL1_BG_IMAGE = "res/background1.png";
    private final static String LEVEL1_FILE = "res/level1.csv";
    private final static Point LEVEL1_INSTRUCTION_BOTTOMLEFT = new Point (350, 350);
    private final static String LEVEL1_INSTRUCTION_MESSAGE = "PRESS SPACE TO START\nPRESS A TO ATTACK\nDEFEAT NAVEC TO WIN";
    private final static int MAX_TIMESCALE = 3;
    private final static int MIN_TIMESCALE = -3;

    public Level1() {
        super(LEVEL1_FILE, LEVEL1_BG_IMAGE, LEVEL1_INSTRUCTION_MESSAGE, LEVEL1_INSTRUCTION_BOTTOMLEFT);
    }

    @Override
    protected void update(Input input, ShadowDimension game) {
        // if 'L' & 'K' pressed, adjust the speed of enemy
        if (input.wasPressed(Keys.L)) {
            if (this.timeScale < MAX_TIMESCALE) {
                this.timeScale += 1;
            }
            System.out.println("Sped up, Speed: " + this.timeScale);
        }

        if (input.wasPressed(Keys.K)) {
            if (this.timeScale > MIN_TIMESCALE) {
                this.timeScale -= 1;
            }
            System.out.println("Slowed down, Speed: " + this.timeScale);
        }

        super.update(input, game);

        // player attack damage to Enemy if pressed 'A'
        if (input.wasPressed(Keys.A)) {
            if (this.getPlayer().isReadyToAttack()) {
                this.getPlayer().setAttackState(1);
            }
        }
        // adjust player attack & invincible state
        this.getPlayer().updateInvincibleState();
        this.getPlayer().updateInvincibleState();

        // check Enemy overlap with other object
        for (Enemy enemy : this.getEnemyObjList()) {
            if (enemy.isActive()) {
                // Enemy invincible state
                enemy.updateInvincibleState();
                // enemy attack player
                enemy.attack(this.getPlayer());

                // moving to opposite direction if Enemy moving out of bounds
                if (enemy.checkOutOfBound(this.getTopLeftBoundary(), this.getBottomRightBoundary())) {
                    enemy.reverseMove();
                }
                // moving to opposite direction if Enemy object overlap with other stationary object
                for (ObjectEntity stationaryObj : this.getGameObjList()) {
                    if (enemy.checkOverlap(stationaryObj)) {
                        enemy.reverseMove();
                    }
                }

                // Enemy auto-move & its speed adjusted based on timeScale value
                enemy.move(timeScale);
                // decreasing Enemy Health if player overlap with Enemy while player is attack
                if (this.getPlayer().isAttack() & this.getPlayer().checkOverlap(enemy) & !enemy.isInvincible()) {
                    enemy.receiveDamage(this.getPlayer().getDamage(), this.getPlayer().getObjectType());
                }
                if (enemy.isDead()) {
                    enemy.setActive(false);
                }
            }
            if (enemy.isActive()) {
                enemy.renderObject();
            }
        }
        this.getPlayer().renderObject();

        // check whether Player defeat Navec or not, render game winning screen if so
        if (this.getEnemyObjList().get(0).isDead()) {
            game.setWinLoseState(1);
        }

    }
}