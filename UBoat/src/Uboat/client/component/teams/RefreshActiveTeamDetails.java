package Uboat.client.component.teams;


import Uboat.client.util.http.HttpClientUtil;
import com.google.gson.Gson;
import dTOUI.ActiveTeamsDTO;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

import static Uboat.client.util.Constants.REFRESHER_TEAMS_DATA;


public class RefreshActiveTeamDetails extends TimerTask {
        private final Consumer<List<ActiveTeamsDTO>> updateTeamsDataConsumer;
        private String contestName;

        public RefreshActiveTeamDetails( Consumer<List<ActiveTeamsDTO>> updateTeamsDataConsumer,String contestName) {
            this.updateTeamsDataConsumer = updateTeamsDataConsumer;
            this.contestName = contestName;
        }


        @Override
        public void run() {
            String finalUrl = HttpUrl
                    .parse(REFRESHER_TEAMS_DATA)
                    .newBuilder()
                    .addQueryParameter("gameTitle", contestName)
                    .build()
                    .toString();
            HttpClientUtil.runAsync(finalUrl, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Gson gson = new Gson();
                    String jsonArrayOfContestData = response.body().string();
                    Map<String, String> mapData = gson.fromJson(jsonArrayOfContestData, Map.class);
                    Map<String,ActiveTeamsDTO> actualData = new HashMap<>();
                    if(mapData.size()>0){
                        Map<String,String> mapTeamsDataJson = gson.fromJson(mapData.get("teamsDataMap"),Map.class);
                        for (String str : mapTeamsDataJson.keySet()){
                            String teamName = gson.fromJson(str,String.class);
                            ActiveTeamsDTO activeTeamsDTO = gson.fromJson(mapTeamsDataJson.get(str),ActiveTeamsDTO.class);
                            actualData.put(teamName,activeTeamsDTO);
                        }
                        ArrayList<ActiveTeamsDTO> retList = new ArrayList<>(actualData.values());
                        updateTeamsDataConsumer.accept(retList);
                    }

                }
            });
        }


}
