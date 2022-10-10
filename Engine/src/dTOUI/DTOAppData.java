package dTOUI;

import engine.Engine;
import uboat.engine.users.UserManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DTOAppData {
    private Set<UserManager> userManagersForAllApp = new HashSet<>();
    private Map<String, Engine> mapUboatUsernameToEngineData = new HashMap<>();

    public synchronized Map<String, Engine> getMapUboatGameTitleToEngineData() {
        return mapUboatUsernameToEngineData;
    }

    public synchronized Set<UserManager> getUserManagersForAllApp() {
        return userManagersForAllApp;
    }

    public synchronized void addToMapUboatGameTitleToEngineData(String uboatUsername, Engine uboatEngine) {
        mapUboatUsernameToEngineData.put(uboatUsername,uboatEngine);
    }

    public synchronized void addToUserManagersForAllApp(UserManager userManager) {
        userManagersForAllApp.add(userManager);
    }

    public synchronized void removeFromMapUboatGameTitleToEngineData(String uboatUsername) {
        mapUboatUsernameToEngineData.remove(uboatUsername);
    }

    public synchronized void removeFromUserManagersForAllApp(UserManager userManager) {
        userManagersForAllApp.remove(userManager);
    }
}