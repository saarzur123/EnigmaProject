package component.refresher;

import Uboat.client.util.http.HttpClientUtil;
import com.google.gson.Gson;
import decryption.manager.DTOMissionResult;
import decryption.manager.Mission;
import decryption.manager.SynchKeyForAgents;
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
    private SynchKeyForAgents key;
    private Consumer<DTOMissionResult> updateMissionResultsInServer;
    public RefresherTakingMissions(Consumer<List<Mission>> consumerTakingOutMissionForAgent,
                                   Consumer<Boolean> consumerNoMoreMissionsLeft, String teamName, int packageAmount,
                                   Consumer<DTOMissionResult> updateMissionResultsInServer, SynchKeyForAgents key) {
        this.consumerTakingOutMissionForAgent = consumerTakingOutMissionForAgent;
        this.consumerNoMoreMissionsLeft =consumerNoMoreMissionsLeft;
        this.teamName = teamName;
        this.packageAmount = packageAmount;
        this.updateMissionResultsInServer = updateMissionResultsInServer;
        this.key = key;
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
                //commit   Mission[] missionsArr = gson.fromJson(mapDoneMissionsAndMissionsList.get("listMissions"),Mission[].class);

                if(!isAllMissionsOut){
                    Mission[] missionsArr = gson.fromJson(mapDoneMissionsAndMissionsList.get("listMissions"),Mission[].class);

                    List<Mission> missionList = Arrays.asList(missionsArr);
                    updateMissionKey(missionList);
                    consumerTakingOutMissionForAgent.accept(missionList);
                }
                else {
                    consumerNoMoreMissionsLeft.accept(isAllMissionsOut);
                }
            }
        });
    }

    private void updateMissionKey(List<Mission> missionDTOS){
        SynchKeyForAgents key = new SynchKeyForAgents();

        for(Mission data : missionDTOS){
            data.setUpdateMissionResultsInServer(updateMissionResultsInServer);
          data.setKey(key);
        }
    }


}