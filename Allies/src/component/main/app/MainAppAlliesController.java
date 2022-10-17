package component.main.app;

import com.google.gson.Gson;
import component.configure.AlliesConfigureController;
import component.configure.tile.ActiveTeamDataController;
import component.contest.ContestDataController;
import component.login.LoginController;
import component.refresh.contest.data.ContestDataAreaRefresher;
import component.refresh.contest.data.ContestTeamsDataAreaRefresher;
import dTOUI.ActiveTeamsDTO;
import dTOUI.ContestDTO;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtilAL;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static util.ConstantsAL.*;

public class MainAppAlliesController {
    @FXML
    private GridPane login;
    @FXML
    private LoginController loginController;
    @FXML private Tab contestTab;
    @FXML private Tab dashboardTab;

    @FXML
    private Label userGreetingLabel;

    @FXML
    private HBox stringEncryptBruteForce;
    @FXML
    private VBox currentContestDataAreaVBOX;
    @FXML
    private VBox currentContestTeamsAreaVBOX;

    ////dashboard - fxml
    @FXML
    private VBox contestsDataArea;
    @FXML
    private ScrollPane agentsDataArea;
    @FXML private TabPane tabPaneAllies;

    private Timer contestDataTimer;
    private Timer teamsDataTimer;
    private List<ActiveTeamsDTO> listCurrentTeams = new ArrayList<>();
    private TimerTask updateContestData;
    private TimerTask updateContestTeamsData;
    private BooleanProperty autoUpdate = new SimpleBooleanProperty();
    private ContestDTO chosenContestData;
    private Map<String, ContestDTO> mapContestNameToContestsDataToShow = new HashMap<>();
    private List<String> listFullContest = new ArrayList<>();



    private String currentBattleFieldName;
    private final StringProperty currentUserName = new SimpleStringProperty(JHON_DOE);

    @FXML
    public void initialize() {

        tabPaneAllies.setDisable(true);
        userGreetingLabel.textProperty().bind(Bindings.concat("Hello ", currentUserName));
        if (loginController != null) {
            loginController.setAlliesMainController(this);
        }
        startUpdateContestsData();

        String finalUrl = HttpUrl
                .parse(INITALIZE_ALLIES)
                .newBuilder()
                .addQueryParameter("gameTitle", currentBattleFieldName)
                .build()
                .toString();

        HttpClientUtilAL.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonListOfFullContest = response.body().string();
                Gson gson = new Gson();

                String[] contestsNameJson = gson.fromJson(jsonListOfFullContest, String[].class);
                if(contestsNameJson.length != 0)
                    listFullContest = Arrays.asList(contestsNameJson);
            }
        });
    }

    public void setListCurrentTeams(List<ActiveTeamsDTO> listCurrentTeams) {
        this.listCurrentTeams = listCurrentTeams;
    }

    public Tab getContestTab() {
        return contestTab;
    }


    public TabPane getTabPaneAllies() {
        return tabPaneAllies;
    }

    public VBox getCurrentContestTeamsAreaVBOX() {
        return currentContestTeamsAreaVBOX;
    }

    public Label getClientErrorLabel() {
        return loginController.getErrorMessageLabel();
    }

    public String getCurrentBattleFieldName() {
        return currentBattleFieldName;
    }

    public void setCurrentBattleFieldName(String currentBattleFieldName) {
        this.currentBattleFieldName = currentBattleFieldName;
    }

    public ContestDTO getChosenContestData() {
        return chosenContestData;
    }

    public static void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occured !");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void updateUserName(String userName) {
        currentUserName.set(userName);
    }

    private void updateContestsDataList(Map<String,ContestDTO> contestData) {
      mapContestNameToContestsDataToShow = contestData;
        Platform.runLater(() -> {
            contestsDataArea.getChildren().clear();
            createContestDataTiles();
            if(chosenContestData!=null) {
                updateCurrentContestDataArea(mapContestNameToContestsDataToShow.get(chosenContestData.getBattleFieldName()));
            }
            });
    }

    private void updateContestTeamsDataList(List<ActiveTeamsDTO> contestTeamsData) {
        listCurrentTeams = contestTeamsData;
        Platform.runLater(() -> {
            updateCurrentContestTeamsArea(listCurrentTeams);
        });
    }

    private void  updateCurrentContestTeamsArea(List<ActiveTeamsDTO> listTeamsData){
        if(!listTeamsData.isEmpty()) {
            currentContestTeamsAreaVBOX.getChildren().clear();
            for (ActiveTeamsDTO teamData : listTeamsData) {
                createContestTeamDataTile(teamData, currentContestTeamsAreaVBOX);
            }
        }
    }

    private void createContestTeamDataTile(ActiveTeamsDTO teamData, VBox teamsDataArea) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/component/configure/tile/activeTeamData.fxml");//
            loader.setLocation(url);
            Node singleTeamData = loader.load();
            ActiveTeamDataController activeTeamsController = loader.getController();
            activeTeamsController.setAlliesController(this);
            activeTeamsController.insertDataToContestTeamTile(teamData.getTeamName(), teamData.getMissionSize(), teamData.getAgentNumber());
            teamsDataArea.getChildren().add(singleTeamData);
        } catch (IOException e) {

        }
    }

    private void createContestDataTiles() {
        for (String contestName : mapContestNameToContestsDataToShow.keySet()) {
            createContestDataTile(mapContestNameToContestsDataToShow.get(contestName));
        }
    }

    private void createContestDataTile(ContestDTO contestData) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource("/component/contest/ContestData.fxml");//
            loader.setLocation(url);
            Node singleContestData = loader.load();
            ContestDataController contestDataController = loader.getController();
            contestDataController.setAlliesController(this);
            boolean bTNDisable = listFullContest.contains(contestData.getBattleFieldName());
            Platform.runLater(()->{

                if(listFullContest.contains(contestData.getBattleFieldName()))
                    contestDataController.insertDataToContest(contestData, true);
                else contestDataController.insertDataToContest(contestData, false);
            contestsDataArea.getChildren().add(singleContestData);


            });
        } catch (IOException e) {

        }
    }

    public void startUpdateContestsData() {
        updateContestData = new ContestDataAreaRefresher(this::updateContestsDataList);
        contestDataTimer = new Timer();
        contestDataTimer.schedule(updateContestData, REFRESH_RATE, REFRESH_RATE);
    }

    public void startUpdateAlliesData() {
        updateContestTeamsData = new ContestTeamsDataAreaRefresher(currentBattleFieldName, this::updateContestTeamsDataList);
        teamsDataTimer = new Timer();
        teamsDataTimer.schedule(updateContestTeamsData, REFRESH_RATE, REFRESH_RATE);
    }

    @FXML
    public void onContestChosenRedayActionBTN(ActionEvent event) throws IOException {
        dashboardTab.setDisable(true);
        setSelectedTab();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/component/configure/AlliesConfigure.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);//block the user from doing other actions
        stage.initStyle(StageStyle.DECORATED);// now te pop up window will have a toolbar
        stage.setTitle("Manually Configuration Window");
        stage.setScene(new Scene(root1));
        AlliesConfigureController alliesConfigureController = fxmlLoader.getController();
        alliesConfigureController.setMainController(this);
        stage.showAndWait();

            String finalUrl = HttpUrl
                    .parse(REFRESH_EXSIST_UBOAT)
                    .newBuilder()
                    .addQueryParameter("gameTitle", currentBattleFieldName)
                    .build()
                    .toString();

        HttpClientUtilAL.runAsync(finalUrl, new Callback(){
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String jsonMapOfData = response.body().string();
                    Gson gson = new Gson();
                    Map<String, String> map = gson.fromJson(jsonMapOfData, Map.class);
                    String contestMapString = map.get("map");

                    //extracting mapContestNameToContestsDataToShow from jsonValuesMap
                    Map<String,ContestDTO> actualData = new HashMap<>();

                        Map<String,String> mapContestDataJson = gson.fromJson(contestMapString,Map.class);
                        for (String str : mapContestDataJson.keySet()){
                            String contestName = gson.fromJson(str,String.class);
                            ContestDTO contestDTO = gson.fromJson(mapContestDataJson.get(str),ContestDTO.class);
                            actualData.put(contestName,contestDTO);
                        }
                    mapContestNameToContestsDataToShow = actualData;


                    if(map.get("full").equals("YES")){
                        String contestDTOName = map.get("contestName");
                        String[] contestsNameJson = gson.fromJson(map.get("listFullContest"), String[].class);
                        listFullContest = Arrays.asList(contestsNameJson);
                    }
                    Platform.runLater(()->{
                        String battleFieldName = getCurrentBattleFieldName();
                        setChosenContest(mapContestNameToContestsDataToShow.get(battleFieldName));

                    });

                }
            });
        }


    public void setSelectedTab(){
        SingleSelectionModel<Tab> selectionModel = tabPaneAllies.getSelectionModel();
        contestTab.setDisable(false);
        selectionModel.select(1);
    }


    public void setChosenContest(ContestDTO chosenContestData){
        this.chosenContestData = chosenContestData;
        this.currentBattleFieldName = chosenContestData.getBattleFieldName();
        startUpdateAlliesData();
        //updateCurrentContestDataArea(this.chosenContestData);
    }

    public void updateCurrentContestDataArea(ContestDTO chosenContestData){
        if(chosenContestData != null) {
            try {
                currentContestDataAreaVBOX.getChildren().clear();
                FXMLLoader loader = new FXMLLoader();
                URL url = getClass().getResource("/component/contest/ContestData.fxml");
                loader.setLocation(url);
                Node singleContestData = loader.load();
                ContestDataController contestDataController = loader.getController();
                contestDataController.setAlliesController(this);
                if (listFullContest.contains(chosenContestData.getBattleFieldName()))
                    contestDataController.insertDataToContest(chosenContestData, true);
                else contestDataController.insertDataToContest(chosenContestData, false);

                currentContestDataAreaVBOX.getChildren().add(singleContestData);
            } catch (IOException e) {

            }
        }
    }

    @FXML
    public void onAddAgentsNumberToNextRoundSubmitBTN(ActionEvent event){

    }

}