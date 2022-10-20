package component.refresher;

import Uboat.client.util.http.HttpClientUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.ConstantsAG.REFRESH_CONTEST_NAME;


public class RefresherContestName extends TimerTask {
    private final Consumer<String> updateAgentContestName;
    private String teamName;

    public RefresherContestName(Consumer<String> updateAgentContestName,String teamName) {
        this.updateAgentContestName = updateAgentContestName;
        this.teamName = teamName;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(REFRESH_CONTEST_NAME)
                .newBuilder()
                .addQueryParameter("teamName", teamName)
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String contestName = response.body().string();
                if(!contestName.equals("")){
                    updateAgentContestName.accept(contestName);
                }
            }
        });
    }


}