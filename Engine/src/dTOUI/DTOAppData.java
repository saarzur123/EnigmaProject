package dTOUI;

import engine.Engine;
import uboat.engine.users.UserManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DTOAppData {
    private Set<UserManager> userManagersForAllApp = new HashSet<>();
    private Map<String, Engine> mapUboatToEngineData = new HashMap<>();

    public Map<String, Engine> getMapUboatToEngineData() {
        return mapUboatToEngineData;
    }

    public Set<UserManager> getUserManagersForAllApp() {
        return userManagersForAllApp;
    }

    public void setMapUboatToEngineData(Map<String, Engine> mapUboatToEngineData) {
        this.mapUboatToEngineData = mapUboatToEngineData;
    }

    public void setUserManagersForAllApp(Set<UserManager> userManagersForAllApp) {
        this.userManagersForAllApp = userManagersForAllApp;
    }
}