package component.refresh.contest.data;

import com.google.gson.Gson;
import dTOUI.ContestDTO;
import javafx.beans.property.BooleanProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtilAL;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.ConstantsAL.REFRESHER_CONTEST_DATA;

public class ContestDataAreaRefresher extends TimerTask {
    private final Consumer<Map<String,ContestDTO>> updateContestDataConsumer;
    private final BooleanProperty shouldUpdate;


    public ContestDataAreaRefresher(BooleanProperty shouldUpdate, Consumer<Map<String,ContestDTO>> updateContestDataConsumer) {
        this.shouldUpdate = shouldUpdate;
        this.updateContestDataConsumer = updateContestDataConsumer;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(REFRESHER_CONTEST_DATA)
                .newBuilder()
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
                Map<String,ContestDTO> actualData = new HashMap<>();
                if(mapData.size()>0){
                    Map<String,String> mapContestDataJson = gson.fromJson(mapData.get("contestsDataMap"),Map.class);
                    for (String str : mapContestDataJson.keySet()){
                        String contestName = gson.fromJson(str,String.class);
                        ContestDTO contestDTO = gson.fromJson(mapContestDataJson.get(str),ContestDTO.class);
                        actualData.put(contestName,contestDTO);
                    }
                    updateContestDataConsumer.accept(actualData);
                }
            }
        });
    }
}
