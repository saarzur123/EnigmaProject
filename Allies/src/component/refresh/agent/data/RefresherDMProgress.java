package component.refresh.agent.data;

import com.google.gson.Gson;
import dTOUI.DTODmProgress;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtilAL;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.ConstantsAL.REFRESHER_DM_PROGRESS;

public class RefresherDMProgress extends TimerTask {
    private final Consumer<DTODmProgress> updateDmProgress;

    private String contestName;
    private String allieName;

    public RefresherDMProgress(String contestName, String allieName,
                                   Consumer<DTODmProgress> updateDmProgress) {
        this.updateDmProgress = updateDmProgress;
        this.contestName = contestName;
        this.allieName = allieName;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(REFRESHER_DM_PROGRESS)
                .newBuilder()
                .addQueryParameter("gameTitle", contestName)
                .addQueryParameter("allieName", allieName)
                .build()
                .toString();

        HttpClientUtilAL.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                String progDataJ = response.body().string();
                DTODmProgress progData = gson.fromJson(progDataJ, DTODmProgress.class);
                    updateDmProgress.accept(progData);
            }
        });
    }
}
