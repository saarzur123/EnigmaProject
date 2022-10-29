package component.refresh.table;

import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import decryption.manager.DTOMissionResult;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import util.http.HttpClientUtilAL;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.ConstantsAL.REFRESHER_ALLIE_CANDIDATE_TABLE;

public class AllieCandidatesRefresher extends TimerTask {
    private final Consumer<List<DTOMissionResult>> updateAllieTable;
    private String gameTitle;
    private String allieName;

    public AllieCandidatesRefresher(Consumer<List<DTOMissionResult>> updateAllieTable, String gameTitle, String allieName) {
        this.updateAllieTable = updateAllieTable;
        this.allieName = allieName;
        this.gameTitle = gameTitle;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(REFRESHER_ALLIE_CANDIDATE_TABLE)
                .newBuilder()
                .addQueryParameter("gameTitle",gameTitle)
                .addQueryParameter("allieName",allieName)
                .build()
                .toString();

        HttpClientUtilAL.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                DTOMissionResult[] results = gson.fromJson(responseBody,DTOMissionResult[].class);
                updateAllieTable.accept(Arrays.asList(results));
            }

        });
    }
}