package Uboat.client.component.candidate.refresh.table;

import Uboat.client.util.http.HttpClientUtil;
import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import decryption.manager.DTOMissionResult;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

import static Uboat.client.util.Constants.REFRESHER_UBOAT_CANDIDATE_TABLE;

public class CandidateRefreshTable extends TimerTask {
    private final Consumer<List<DTOMissionResult>> updateUboatTable;
    private String gameTitle;

    public CandidateRefreshTable(Consumer<List<DTOMissionResult>> updateUboatTable, String gameTitle) {
        this.updateUboatTable = updateUboatTable;
        this.gameTitle = gameTitle;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(REFRESHER_UBOAT_CANDIDATE_TABLE)
                .newBuilder()
                .addQueryParameter("gameTitle",gameTitle)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseBody = response.body().string();
                Gson gson = new Gson();
                DTOMissionResult[] results = gson.fromJson(responseBody,DTOMissionResult[].class);
                updateUboatTable.accept(Arrays.asList(results));
            }

        });
    }
}