package component.progress;

import component.main.app.MainAppAlliesController;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProgressController {

    @FXML
    private Label totalMissionExsistLBL;

    @FXML
    private Label totalMissionProducedLBL;

    @FXML
    private Label totalMissionsDoneLBL;

    private MainAppAlliesController alliesController;

    private SimpleStringProperty missionExist = new SimpleStringProperty();

    private SimpleStringProperty missionProduced= new SimpleStringProperty();
    private SimpleStringProperty missionsDone= new SimpleStringProperty();



    public void setAlliesController(MainAppAlliesController alliesController) {
        this.alliesController = alliesController;
        totalMissionExsistLBL.textProperty().bind(missionExist);
        totalMissionProducedLBL.textProperty().bind(missionProduced);
        totalMissionsDoneLBL.textProperty().bind(missionsDone);
    }

    public SimpleStringProperty getMissionExist(){return missionExist;}
    public int getMissionProduced(){
        return Integer.parseInt(missionProduced.get());
    }
    public int getMissionsDone(){
        return Integer.parseInt(missionsDone.get());
    }

    public void setMissionProduced(long toAdd){
        long save = Integer.parseInt(missionProduced.get());
        long total = save+toAdd;
        missionProduced.set(String.valueOf(total));
    }

    public void setMissionDone(long toAdd){
        long save = Integer.parseInt(missionsDone.get());
        long total = save+toAdd;
        missionsDone.set(String.valueOf(total));
    }
}

