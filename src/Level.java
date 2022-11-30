/**
 * Level Abstract Class that contain attribute and method in all level
 *
 * @author Hogi Kwak
 */

import bagel.*;
import bagel.util.Point;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public abstract class Level {
    private Point topLeftBoundary;
    private Point bottomRightBoundary;
    private Player player;
    private ArrayList<ObjectEntity> gameObjList;
    private ArrayList<Enemy> enemyObjList;
    private final Image BG_IMAGE;
    private final String LEVEL_FILE;
    private final Font INSTRUCTION_MESSAGE = new Font("res/frostbite.ttf", 40);
    private final Point LEVEL_INSTRUCTION_BOTTOMLEFT;
    private final String LEVEL_INSTRUCTION_MESSAGE;

    public Level(String levelFile, String levelBGImage) {
        this.LEVEL_FILE = levelFile;
        this.BG_IMAGE = new Image(levelBGImage);
        this.LEVEL_INSTRUCTION_MESSAGE = null;
        this.LEVEL_INSTRUCTION_BOTTOMLEFT = null;
    }

    public Level(String levelFile, String levelBGImage, String levelInstructionMessage,
                 Point levelInstructionBottomLeft) {
        this.LEVEL_FILE = levelFile;
        this.BG_IMAGE = new Image(levelBGImage);
        this.LEVEL_INSTRUCTION_MESSAGE = levelInstructionMessage;
        this.LEVEL_INSTRUCTION_BOTTOMLEFT = levelInstructionBottomLeft;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * get ArrayList<ObjectEntity> Object (Wall, Tree, and SinkHole)
     * @return ArrayList of Game object in this level
     */
    public ArrayList<ObjectEntity> getGameObjList() {
        return gameObjList;
    }

    /**
     * get ArrayList<Enemy> Object (Demon and Navec)
     * @return ArrayList of Enemy object in this level
     */
    public ArrayList<Enemy> getEnemyObjList() {
        return enemyObjList;
    }


    public Point getTopLeftBoundary() {
        return topLeftBoundary;
    }

    public Point getBottomRightBoundary() {
        return bottomRightBoundary;
    }

    public void readLevel() {
        this.readCSV(this.LEVEL_FILE);
    }

    private void readCSV(String fileName) {
        String line, objectType;
        double objectX, objectY;
        this.gameObjList = new ArrayList<>();
        this.enemyObjList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                // read data from CSV file and initialize Game Entity Object based on its type (CSV first column data)
                objectType = line.split(",", 3)[0].toLowerCase();
                objectX = Double.parseDouble(line.split(",", 3)[1]);
                objectY = Double.parseDouble(line.split(",", 3)[2]);
                switch (objectType) {
                    case "fae":
                        this.player = new Player(objectX, objectY);
                        break;
                    case "navec":
                        this.enemyObjList.add(0, new Navec(objectX, objectY));
                        break;
                    case "demon":
                        this.enemyObjList.add(new Demon(objectX, objectY));
                        break;
                    case "wall":
                        this.gameObjList.add(new Wall(objectX, objectY));
                        break;
                    case "tree":
                        this.gameObjList.add(new Tree(objectX, objectY));
                        break;
                    case "sinkhole":
                        this.gameObjList.add(new Sinkhole(objectX, objectY));
                        break;
                    case "topleft":
                        topLeftBoundary = new Point(objectX, objectY);
                        break;
                    case "bottomright":
                        bottomRightBoundary = new Point(objectX, objectY);
                        break;
                    default:
                        System.out.println("Error: LEVEL.csv contain UNKNOWN object");
                        System.exit(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void update(Input input, ShadowDimension game) {
        this.BG_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        this.player.move(input);

        // if player is out of the game boundary, revert that movement
        if (this.player.checkOutOfBound(topLeftBoundary, bottomRightBoundary)) {
            this.player.setPrevPosition();
        }

        // if  player are in an overlap, revert that movement / decrease health
        for (ObjectEntity obj : this.gameObjList) {
            if (obj.isActive() & this.player.checkOverlap(obj)) {
                switch (obj.getObjectType()) {
                    case "Wall":
                    case "Tree":
                        this.player.setPrevPosition();
                        break;
                    case "SinkHole":
                        this.player.receiveDamage(Sinkhole.getSinkHoleDmg(), obj.getObjectType());
                        obj.setActive(false);
                        break;
                }
            }
            if (obj.isActive()) {
                obj.renderObject();
            }
        }

        // check whether player health drops to 0 or not, if YES, change gameStatus to Losing
        if (this.player.isDead()) {
            game.setWinLoseState(-1);
        }
    }

    protected void renderInstructionScreen() {
        if (this.LEVEL_INSTRUCTION_MESSAGE != null & this.LEVEL_INSTRUCTION_BOTTOMLEFT != null) {
            this.INSTRUCTION_MESSAGE.drawString(this.LEVEL_INSTRUCTION_MESSAGE
                    , this.LEVEL_INSTRUCTION_BOTTOMLEFT.x, this.LEVEL_INSTRUCTION_BOTTOMLEFT.y);
        }
    }

}
