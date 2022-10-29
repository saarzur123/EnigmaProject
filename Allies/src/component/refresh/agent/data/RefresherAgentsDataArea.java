package component.refresh.agent.data;

import com.google.gson.Gson;
import dTOUI.DTOActiveAgent;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtilAL;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.ConstantsAL.REFRESHER_AGENTS_DATA_AREA;

public class RefresherAgentsDataArea extends TimerTask {
    private final Consumer<List<DTOActiveAgent>> updateAgentDataArea;
    private String contestName;
    private String allieName;

    public RefresherAgentsDataArea(String contestName,String allieName, Consumer<List<DTOActiveAgent>> updateAgentDataArea) {
        this.updateAgentDataArea = updateAgentDataArea;
        this.contestName = contestName;
        this.allieName = allieName;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(REFRESHER_AGENTS_DATA_AREA)
                .newBuilder()
                .addQueryParameter("gameTitle",contestName)
                .addQueryParameter("allieName",allieName)
                .build()
                .toString();

        HttpClientUtilAL.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                String agentDataListJson = response.body().string();
                DTOActiveAgent[] agentDataArray = gson.fromJson(agentDataListJson,DTOActiveAgent[].class);
                if(agentDataArray.length>0){
                    updateAgentDataArea.accept(Arrays.asList(agentDataArray));
                }
            }
        });
    }
}
