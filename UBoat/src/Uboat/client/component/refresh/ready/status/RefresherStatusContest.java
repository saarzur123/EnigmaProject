package Uboat.client.component.refresh.ready.status;

import Uboat.client.util.http.HttpClientUtil;
import com.google.gson.Gson;
import dTOUI.ContestDTO;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.function.Consumer;


public class RefresherStatusContest extends TimerTask {
    private final Consumer<Map<String, ContestDTO>> updateContestDataConsumer;

    public RefresherStatusContest(Consumer<Map<String, ContestDTO>> updateContestDataConsumer) {
        this.updateContestDataConsumer = updateContestDataConsumer;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(REFRESHER_CONTEST_DATA)
                .newBuilder()
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
                Map<String,ContestDTO> actualDataContests = new HashMap<>();

                if(mapData.size()>0){

                    Map<String,String> mapContestDataJson = gson.fromJson(mapData.get("contestsDataMap"),Map.class);
                    for (String str : mapContestDataJson.keySet()){
                        String contestName = gson.fromJson(str,String.class);
                        ContestDTO contestDTO = gson.fromJson(mapContestDataJson.get(str),ContestDTO.class);
                        actualDataContests.put(contestName,contestDTO);
                    }
                    updateContestDataConsumer.accept(actualDataContests);
                }
            }

        });

    }
}
