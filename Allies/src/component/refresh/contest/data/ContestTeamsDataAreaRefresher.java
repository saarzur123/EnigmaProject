package component.refresh.contest.data;

import com.google.gson.Gson;
import dTOUI.ActiveTeamsDTO;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtilAL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.ConstantsAL.REFRESHER_TEAMS_DATA;

public class ContestTeamsDataAreaRefresher extends TimerTask {
    private final Consumer<List<ActiveTeamsDTO>> updateContestTeamsArea;
    private final Consumer<Boolean> readyToStart;
    private String contestName;
    private String allieName;
    private Boolean ready = false;


    public ContestTeamsDataAreaRefresher(String contestName, Consumer<List<ActiveTeamsDTO>> updateContestTeamsArea, String allieName, Consumer<Boolean> readyToStart) {
        this.updateContestTeamsArea = updateContestTeamsArea;
        this.contestName = contestName;
        this.allieName = allieName;
        this.readyToStart = readyToStart;
    }

    public Boolean isReady() {
        return ready;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(REFRESHER_TEAMS_DATA)
                .newBuilder()
                .addQueryParameter("gameTitle",contestName)
                .addQueryParameter("alliesName",allieName)
                .build()
                .toString();

        HttpClientUtilAL.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                String jsonArrayOfContestData = response.body().string();
                Map<String, String> mapData = gson.fromJson(jsonArrayOfContestData, Map.class);

                    if(mapData.size()>0){
                        List<ActiveTeamsDTO> actualDataTeams = new ArrayList<>();
                            Map<String, String> mapTeamsDataJson = gson.fromJson(mapData.get("teamsDataMap"), Map.class);
                            for (String str : mapTeamsDataJson.keySet()) {
                                ActiveTeamsDTO activeTeamsDTO = gson.fromJson(mapTeamsDataJson.get(str), ActiveTeamsDTO.class);
                                actualDataTeams.add(activeTeamsDTO);
                            updateContestTeamsArea.accept(actualDataTeams);
                        }
                          if(mapData.containsKey("ready"))
                              readyToStart.accept(true);
                    }
                }
        });
    }
}