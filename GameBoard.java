
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {

    private Color backGroundColor;
    private State state = State.STOP;
    private int level;
    public static final int sellSize = 20;
    private final int numOfRow = 20;
    private final int numOfColumn = 15;
    private boolean[][] fillCells = new boolean[numOfRow][numOfColumn];
    private Tetris tetris;
    List<Brick> bricks = new ArrayList<>();

    GameBoard(int level, Tetris tetris) {
        this.level = level;
        this.tetris = tetris;
    }


    public Color getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void start() {
        state = State.START;

        Random random = new Random();
        Brick brick = null;
        int randomBrick = random.nextInt(10);

        switch (randomBrick) {
            case 1:
                brick = new BrickOne(this);
                break;
            case 2:
                brick = new BrickTwo(this);
                break;
            case 3:
                brick = new BrickThree(this);
                break;
            case 4:
                brick = new BrickFour(this);
                break;
            case 5:
                brick = new BrickFive(this);
                break;
            case 6:
                brick = new BrickSix(this);
                break;
            case 7:
                brick = new BrickSeven(this);
                break;
            case 8:
                brick = new BrickEight(this);
                break;
            case 9:
                brick = new BrickNine(this);

        }

        bricks.add(brick);
    }

    public void stop() {
        state = State.STOP;
    }

    public void moveDown() {

    }

    public void move(Direction direction) {
        switch (direction) {
            case LEFT:
                break;
            case RIGHT:
                break;
        }

    }

    public boolean isFilled(int x, int y) {
        return fillCells[x][y];
    }

    public void fill(int x, int y) {
        fillCells[x][y] = true;
    }

    public void paint() {

    }

}
