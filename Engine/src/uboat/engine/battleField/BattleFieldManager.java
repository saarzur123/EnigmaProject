package uboat.engine.battleField;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BattleFieldManager {
    private final Set<String> gameTitles;

    public BattleFieldManager() {
        gameTitles = new HashSet<>();
    }

    public synchronized void addGameTitle(String gameTitle) {
        gameTitles.add(gameTitle);
    }

    public synchronized void removeGameTitle(String gameTitle) {
        gameTitles.remove(gameTitle);
    }

    public synchronized Set<String> getGameTitles() {
        return Collections.unmodifiableSet(gameTitles);
    }

}
