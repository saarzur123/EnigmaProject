package component.status;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtilAL;

import java.io.IOException;
import java.util.Map;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.ConstantsAL.REFRESHER_CONTEST_STATUS;


public class RefresherStatusContest extends TimerTask {
    private String contestName;

    public RefresherStatusContest( String contestName) {
        this.contestName = contestName;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(REFRESHER_CONTEST_STATUS)
                .newBuilder()
                .addQueryParameter("contestName", contestName)
                .build()
                .toString();

        HttpClientUtilAL.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonArrayOfContestData = response.body().string();
            }

        });

    }
}
