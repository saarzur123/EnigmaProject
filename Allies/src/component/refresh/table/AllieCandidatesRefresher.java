package component.refresh.table;

import decryption.manager.DTOMissionResult;

import java.util.TimerTask;
import java.util.function.Consumer;

public class AllieCandidatesRefresher extends TimerTask {
    private final Consumer<DTOMissionResult> updateAllieTable;

    public AllieCandidatesRefresher(Consumer<DTOMissionResult> updateAllieTable) {
        this.updateAllieTable = updateAllieTable;
    }

    @Override
    public void run() {
//        String finalUrl = HttpUrl
//                .parse(REFRESHER_CONTEST_DATA)
//                .newBuilder()
//                .build()
//                .toString();
//
//        HttpClientUtilAL.runAsync(finalUrl, new Callback() {
//
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//            }
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//
//            }
//
//        });
    }
}