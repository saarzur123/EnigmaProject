package component.refresher;

import Uboat.client.util.http.HttpClientUtil;
import com.google.gson.Gson;
import dTOUI.DTOActiveAgent;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.TimerTask;

import static util.ConstantsAG.ADD_AGENT_DATA_TO_MAP;

public class RefresherAgentDataInAppData extends TimerTask {
    private String teamName;
    private String contestName;
    private DTOActiveAgent agentToUpdate;

    public RefresherAgentDataInAppData(String contestName, String teamName, DTOActiveAgent agentToUpdate) {
        this.teamName = teamName;
        this.contestName = contestName;
        this.agentToUpdate = agentToUpdate;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(ADD_AGENT_DATA_TO_MAP)
                .newBuilder()
                .addQueryParameter("contestName", contestName)
                .addQueryParameter("allieName", teamName)
                .addQueryParameter("agentData", new Gson().toJson(agentToUpdate))
                .build()
                .toString();

            HttpClientUtil.runAsync(finalUrl, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseBody = response.body().string();
                }
            });

    }
}
