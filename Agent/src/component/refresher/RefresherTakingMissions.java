package component.refresher;

import Uboat.client.util.http.HttpClientUtil;
import com.google.gson.Gson;
import decryption.manager.Mission;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.ConstantsAG.REFRESH_TAKING_MISSIONS;

public class RefresherTakingMissions extends TimerTask {
    private final Consumer<List<Mission>> consumerTakingOutMissionForAgent;
    private final Consumer<Boolean> consumerNoMoreMissionsLeft;
    private String teamName;
    private int packageAmount;
    public RefresherTakingMissions(Consumer<List<Mission>> consumerTakingOutMissionForAgent,Consumer<Boolean> consumerNoMoreMissionsLeft, String teamName, int packageAmount) {
        this.consumerTakingOutMissionForAgent = consumerTakingOutMissionForAgent;
        this.consumerNoMoreMissionsLeft =consumerNoMoreMissionsLeft;
        this.teamName = teamName;
        this.packageAmount = packageAmount;
    }


    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(REFRESH_TAKING_MISSIONS)
                .newBuilder()
                .addQueryParameter("teamName", teamName)
                .addQueryParameter("packageAmount", new Gson().toJson(packageAmount))
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                String jsonRetMap = response.body().string();
                Map<String,String> mapDoneMissionsAndMissionsList = gson.fromJson(jsonRetMap, Map.class);

                boolean isAllMissionsOut = gson.fromJson(mapDoneMissionsAndMissionsList.get("isAllMissionsOut"),Boolean.class);
                if(!isAllMissionsOut){
                    Mission[] missionsArr = gson.fromJson(mapDoneMissionsAndMissionsList.get("listMissions"),Mission[].class);
                    consumerTakingOutMissionForAgent.accept(Arrays.asList(missionsArr));
                }
                else {
                    consumerNoMoreMissionsLeft.accept(isAllMissionsOut);
                }
            }
        });
    }


}