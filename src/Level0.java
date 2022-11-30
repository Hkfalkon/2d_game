/**
 * Level0 class
 *
 * @author Hogi Kwak
 */

import bagel.*;
import bagel.util.Point;

public class Level0 extends Level {

    private final static String LEVEL0_BG_IMAGE = "res/background0.png";
    private final static String LEVEL0_FILE = "res/level0.csv";
    private final static Point LEVEL0_WINNING_POINT = new Point (950, 670);

    public Level0() {
        super(LEVEL0_FILE, LEVEL0_BG_IMAGE);
    }

    @Override
    protected void update(Input input, ShadowDimension game) {
        super.update(input, game);

        this.getPlayer().renderObject();

        // if player reaches the winning point
        // set LevelComplete = true for rendering level complete screen
        if (this.getPlayer().getPosition().x >= LEVEL0_WINNING_POINT.x &&
                this.getPlayer().getPosition().y >= LEVEL0_WINNING_POINT.y) {
            game.setLevelComplete(true);
        }
    }
}
