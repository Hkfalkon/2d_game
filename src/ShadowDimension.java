/**
 * ShadowDimension for controlling game
 *
 * @author Hogi Kwak
 */

import bagel.*;
import bagel.util.Point;
import java.util.ArrayList;
import java.io.BufferedReader;

public class ShadowDimension extends AbstractGame {
    /**
     * State of the game defined as -2 = Main Starting Screen
     */
    private int gameState = -2;
    /**
     * Win / Lose state of the game defined as the following:
     *  0 = Normal (Game still in process, neither Win / Lose yet)
     *  1 = Winning (Rendering Winning Screen)
     *  -1 = Losing (Rendering Losing Screen)
     */
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DIMENSION";
    private final static String MAIN_INSTRUCTION_MESSAGE = "PRESS SPACE TO START\nUSE ARROW KEYS TO FIND GATE";
    private final static String LEVEL_COMPLETE_MESSAGE = "LEVEL COMPLETE!";
    private final static String WIN_MESSAGE = "CONGRATULATIONS!";
    private final static String LOSE_MESSAGE = "GAME OVER!";
    private int winLoseState = 0;
    private Level currentLevel;
    //
    private int currentLevelIndex = 0;
    // keep all Level data to read LEVEL
    private ArrayList<Level> levelList = new ArrayList<>();
    private int timeScale = 0;
    private final static int REFRESH_RATE = 60;
    private final static int COMPLETE_SCREEN_TIME = 3;
    private final static int autoTIME = COMPLETE_SCREEN_TIME * REFRESH_RATE;
    private boolean isLevelComplete = false;
    private final static int TITLE_FONT_SIZE = 75;
    private final static int INSTRUCTION_FONT_SIZE = 40;
    private final Font TITLE_MESSAGE = new Font("res/frostbite.ttf", TITLE_FONT_SIZE);
    private final Font INSTRUCTION_MESSAGE = new Font("res/frostbite.ttf", INSTRUCTION_FONT_SIZE);
    private final static Point TITLE_BL = new Point (260, 250);
    private final static Point MAIN_INSTRUCTION_BL = new Point (TITLE_BL.x + 90
            , TITLE_BL.y + 190);


    public ShadowDimension(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        this.raedAllLevel();
    }

    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
    }

    /**
     * set setLevelComplete for LevelComplete screen
     * @param levelComplete boolean if currentLevel is complete or not
     */
    public void setLevelComplete(boolean levelComplete) {
        isLevelComplete = levelComplete;
    }

    /**
     * read all level data into an ArrayList<Level>
     */
    public void raedAllLevel() {
        levelList.add(new Level0());
        levelList.add(new Level1());
    }

    /**
     * set WinLoseState to control game state
     * @param winLoseState the number for setting gameState
     */
    public void setWinLoseState(int winLoseState) {
        this.winLoseState = winLoseState;
    }


    @Override
    protected void update(Input input) {

        // if pressed 'ESCAPE' closed the game window regardless of the gameState
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // if pressed 'W' skip to LEVEL1 instruction page
        if (input.wasPressed(Keys.W)) {
            // instruction
            this.gameState = -1;
            // level 1
            this.currentLevelIndex = 1;
            this.currentLevel = this.levelList.get(currentLevelIndex);
        }

        // if the level is completed,
        // rendering complete screen for 3 seconds before reading the next level
        if (this.isLevelComplete) {
            renderMiddleScreen(LEVEL_COMPLETE_MESSAGE);
            // change to instruction screen for level 1 after 3 seconds
            this.timeScale += 1;
            if (timeScale == autoTIME) {
                this.isLevelComplete = false;
                this.timeScale = 0;
                // instruction
                this.gameState = -1;
                this.currentLevel = levelList.get(++this.currentLevelIndex);
            }
        }
        // running game
        else {
            switch (winLoseState) {
                // losing screen
                case -1:
                    renderMiddleScreen(LOSE_MESSAGE);
                    break;
                // in progress game
                case 0:
                    switch (gameState) {
                        // title instruction
                        // render and read starting screen when user press 'SPACE' key
                        case -2:
                            TITLE_MESSAGE.drawString(GAME_TITLE, TITLE_BL.x, TITLE_BL.y);
                            INSTRUCTION_MESSAGE.drawString(MAIN_INSTRUCTION_MESSAGE
                                    , MAIN_INSTRUCTION_BL.x, MAIN_INSTRUCTION_BL.y);

                            if (input.wasPressed(Keys.SPACE)) {
                                this.readNextLevel();
                            }
                            break;
                        // render and read LEVEL1 instruction screen when user press 'SPACE' key
                        case -1:
                            this.currentLevel.renderInstructionScreen();
                            if (input.wasPressed(Keys.SPACE)) {
                                this.readNextLevel();
                            }
                            break;
                        // read LEVEL based on currentLevel data
                        default:
                            this.currentLevel.update(input, this);
                            break;
                    }
                    break;
                // winning screen
                case 1:
                    renderMiddleScreen(WIN_MESSAGE);
                    break;
            }
        }
    }

    /**
     * Rendering the Default Screen in the middle of the screen
     * @param message will be rendering
     */
    public void renderMiddleScreen(String message) {
        TITLE_MESSAGE.drawString(message
                , Window.getWidth() / 2.0 - TITLE_MESSAGE.getWidth(message) / 2
                , Window.getHeight() / 2.0 + TITLE_FONT_SIZE / 2);
    }

    /**
     * assign nextLevel data to currentLevel variable
     */
    public void readNextLevel() {
        this.currentLevel = levelList.get(currentLevelIndex);
        this.currentLevel.readLevel();
        this.gameState = currentLevelIndex;
    }

}
