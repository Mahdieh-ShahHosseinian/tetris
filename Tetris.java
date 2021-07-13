public class Tetris {

    private int level = 1;
    private GameBoard gameBoard = new GameBoard(level, this);
    private int score = 0;
    private int heart = 3;// I don't know!
    private SettingManager settingManager = new SettingManager(gameBoard);
    private ScoresManager scoresManager = new ScoresManager();

    //getter & setter
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }
    //getter & setter finish

    public void load() {
        settingManager.load();
        scoresManager.load();
    }

    public void save() {
        settingManager.save();
        scoresManager.save();
    }

    public void moveDown() {
        gameBoard.moveDown();
    }

    public void move(Direction direction) {
        gameBoard.move(direction);
    }

    public void start() {
        gameBoard.start();
    }

    public void stop() {
        gameBoard.stop();
    }

}
