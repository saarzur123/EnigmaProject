package component.refresh.contest.data;

import com.google.gson.Gson;
import dTOUI.ActiveTeamsDTO;
import dTOUI.ContestDTO;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtilAL;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

import static util.ConstantsAL.REFRESHER_CONTEST_DATA;

public class ContestDataAreaRefresher extends TimerTask {
    private final Consumer<Map<String,ContestDTO>> updateContestDataConsumer;
    private final Consumer<List<ActiveTeamsDTO>> updateContestTeamsArea;
    private String contestName;


    public ContestDataAreaRefresher(String contestName, Consumer<Map<String,ContestDTO>> updateContestDataConsumer, Consumer<List<ActiveTeamsDTO>> updateContestTeamsArea) {
        this.updateContestDataConsumer = updateContestDataConsumer;
        this.updateContestTeamsArea = updateContestTeamsArea;
        this.contestName = contestName;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(REFRESHER_CONTEST_DATA)
                .newBuilder()
                .addQueryParameter("gameTitle",contestName)
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
                Map<String,ContestDTO> actualDataContests = new HashMap<>();
                List<ActiveTeamsDTO> actualDataTeams = new ArrayList<>();

                if(mapData.size()>0){
                    Map<String,String> mapContestDataJson = gson.fromJson(mapData.get("contestsDataMap"),Map.class);
                    for (String str : mapContestDataJson.keySet()){
                        String contestName = gson.fromJson(str,String.class);
                        ContestDTO contestDTO = gson.fromJson(mapContestDataJson.get(str),ContestDTO.class);
                        actualDataContests.put(contestName,contestDTO);
                    }

                    if(mapData.size()>1) {
                        Map<String, String> mapTeamsDataJson = gson.fromJson(mapData.get("teamsDataMap"), Map.class);
                        for (String str : mapTeamsDataJson.keySet()) {
                            String teamName = gson.fromJson(str, String.class);
                            ActiveTeamsDTO activeTeamsDTO = gson.fromJson(mapTeamsDataJson.get(str), ActiveTeamsDTO.class);
                            actualDataTeams.add(activeTeamsDTO);
                        }
                        updateContestTeamsArea.accept(actualDataTeams);
                    }
                    updateContestDataConsumer.accept(actualDataContests);
                }
            }
        });
    }
}
