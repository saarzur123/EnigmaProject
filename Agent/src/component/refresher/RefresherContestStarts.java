package component.refresher;

import Uboat.client.util.http.HttpClientUtil;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.ConstantsAG.REFRESH_CONTEST_STATUS;

public class RefresherContestStarts extends TimerTask {
  private final Consumer<Boolean> updateContestStatus;
    private String contestName;

    public RefresherContestStarts(Consumer<Boolean> updateContestStatus,String contestName) {
       this.updateContestStatus = updateContestStatus;
       this.contestName=contestName;
    }


    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(REFRESH_CONTEST_STATUS)
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
                String jsonContestStatus = response.body().string();
                Boolean contestStatus = gson.fromJson(jsonContestStatus, Boolean.class);
               updateContestStatus.accept(contestStatus);
            }
        });
    }


}