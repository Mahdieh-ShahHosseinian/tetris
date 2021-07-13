public class Player {
    private int score;
    private String name;

    public Player() {

    }

    public Player(int score, String name) {
        this.score = score;
        this.name = name;
    }

    //setter & getter
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
