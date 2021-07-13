import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoresManager {

    List<Player> topPlayers = new ArrayList<>();

    public void clearAllPlayers() {
        topPlayers.removeAll(topPlayers);
    }

    public void addPlayer(Player newPlayer) {

        topPlayers.add(newPlayer);
        topPlayers.sort(new Comparator<Player>() {

            @Override
            public int compare(Player player1, Player player2) {
                return Integer.compare(player2.getScore(), player1.getScore());
            }
        });
        if (topPlayers.size() == 11) topPlayers.remove(10);
    }

    public void load() {

    }

    public void save() {

    }
}
