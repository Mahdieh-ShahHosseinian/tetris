import javafx.scene.paint.Color;

public class SettingManager {
    GameBoard gameBoard;
    int scoresPerRow = 10;
    int startLevel = 1;
    Color backGroundColor = Color.YELLOW;

    SettingManager(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    //setter & getters
    public Color getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
        gameBoard.setBackGroundColor(backGroundColor);
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int getScoresPerRow() {
        return scoresPerRow;
    }

    public void setScoresPerRow(int scoresPerRow) {
        this.scoresPerRow = scoresPerRow;
    }

    public int getStartLevel() {
        return startLevel;
    }

    public void setStartLevel(int startLevel) {
        this.startLevel = startLevel;
        gameBoard.setLevel(startLevel);
    }

    public void load() {

    }

    public void save() {

    }

    public void clearScores() {

    }
}
