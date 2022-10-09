package Uboat.client.component.upload.file;

import Uboat.client.component.main.UboatMainController;
import enigmaException.xmlException.XMLException;
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
        FileChooser fc = new FileChooser();
        File fileDialog = fc.showOpenDialog(null);
        if(fileDialog != null){
            try{

                String path = fileDialog.getAbsolutePath();
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

                    }
                });


                uboatMainController.getEngineCommand().createMachineFromXML(path);
                setOnValidMachine();
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

    private void setOnValidMachine(){
        uboatMainController.getMachineDetailsController().deleteCurrMachine();
        isValidMachine.setValue(false);
        uboatMainController.setCurrMachineTxt();
        //  mainController.setDecryptionTab();
    }
}