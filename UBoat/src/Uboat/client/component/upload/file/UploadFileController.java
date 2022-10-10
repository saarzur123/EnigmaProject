package Uboat.client.component.upload.file;

import Uboat.client.component.main.UboatMainController;
import enigmaException.xmlException.ExceptionDTO;
import enigmaException.xmlException.XMLException;
import handle.xml.check.CheckXML;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Uboat.client.util.Constants.UPLOAD_FILE;

public class UploadFileController {

    @FXML    private Button selectFXMLFileBTN;
    private UboatMainController uboatMainController;
    public final static OkHttpClient HTTP_CLIENT = new OkHttpClient();

    private boolean isFirstMachine = true;

    private BooleanProperty isValidMachine = new SimpleBooleanProperty();

    public BooleanProperty getIsValidMachine(){return isValidMachine;}

    public void setUboatMainController(UboatMainController main){
        uboatMainController = main;
    }

    @FXML
    void selectXMLFile(ActionEvent event) throws IOException {
        uboatMainController.getClientErrorLabel().setText("");
        FileChooser fc = new FileChooser();
        File fileDialog = fc.showOpenDialog(null);
        if(fileDialog != null){
            try{
                String path = fileDialog.getAbsolutePath();
                if(validateFilePath(path)) {
                    File f = new File(path);
                    RequestBody body =
                            new MultipartBody.Builder()
                                    .addFormDataPart("file1", f.getName(), RequestBody.create(f, MediaType.parse("text/plain")))
                                    //.addFormDataPart("key1", "value1") // you can add multiple, different parts as needed
                                    .build();

                    Request request = new Request.Builder()
                            .url(UPLOAD_FILE)
                            .post(body)
                            .build();

                    Call call = HTTP_CLIENT.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String machineDetails = response.body().string();
                            Platform.runLater(() ->
                                    setOnValidMachine(machineDetails)

                            );
                        }
                    });
                }
//                mainController.setSelectedTab();
//                mainController.clearAllTFInEncrypt();
//                mainController.getDictionaryController().SetDictionaryController();
//                mainController.getAgentsController().setAgentsMaxSlider();
            }
            catch (XMLException error){
                uboatMainController.showErrorPopup(error.getMessage());
            }
        }
    }

    private boolean validateFilePath(String path){
        CheckXML xmlValidator = new CheckXML();
        List<ExceptionDTO> checkedObjectsList = new ArrayList<>();
        xmlValidator.checkIfTheFileExist(path,checkedObjectsList);
        xmlValidator.checkFileEnding(path,checkedObjectsList);

        if(checkedObjectsList.size()>0){
            StringBuilder errorMSG = new StringBuilder();
            for(ExceptionDTO err : checkedObjectsList){
                errorMSG.append(err.getMsg());
            }
            uboatMainController.getClientErrorLabel().setText(errorMSG.toString());
            return false;
        }
        return true;
    }

    private void setOnValidMachine(String machineDetails){
        uboatMainController.getMachineDetailsController().deleteCurrMachine();
        isValidMachine.setValue(false);
        uboatMainController.setCurrMachineTxt(machineDetails);
        uboatMainController.unDisableMachineDetails();
        //  mainController.setDecryptionTab();
    }
}