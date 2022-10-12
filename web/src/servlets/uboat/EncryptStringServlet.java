package servlets.uboat;

import com.google.gson.Gson;
import dTOUI.DTOAppData;
import dTOUI.DTOMachineDetails;
import engine.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "EncryptStringServlet", urlPatterns = "/loginShortResponse/encrypt")

public class EncryptStringServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String gameTitle = request.getParameter("gameTitle");
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Map<String, Engine> map = appData.getMapUboatGameTitleToEngineData();
        Engine engine = map.get(gameTitle);

        String stringToEncrypt = request.getParameter("stringToEncrypt");
        String encryptSmallLetters = stringToEncrypt.toLowerCase();
        String DecryptString = "";
        if(engine.getDecryptionManager().getDictionary().isStringInDictionary(encryptSmallLetters)) {
            DecryptString = engine.processData(stringToEncrypt, false);
            //uboatMainController.getMachineDetailsController().updateCurrMachineDetails("k");

        }
        DTOMachineDetails dtoMachineDetails = engine.getMachineDetailsPresenter().createCurrMachineDetails();
        String machineDetails = String.format("%s",engine.showLastMachineDetails(dtoMachineDetails));
        //String jsonEngine = gson.toJson(engine);
        Map<String,Object> resourceNameToValueMap =  new HashMap<>();
        addDataToMap(resourceNameToValueMap, DecryptString, machineDetails);

        String json = gson.toJson(resourceNameToValueMap);
        response.getWriter().println(json);
    }
    private void addDataToMap(Map<String, Object> resourceNameToValueMap,  String DecryptString, String machineDetails){
        resourceNameToValueMap.put("DecryptString", DecryptString);
        resourceNameToValueMap.put("machineDetails", machineDetails);

    }

}


