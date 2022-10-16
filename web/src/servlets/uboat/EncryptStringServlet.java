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
        Map<String,Object> resourceNameToValueMap =  new HashMap<>();
        Engine engine = map.get(gameTitle);

        String stringToEncrypt = request.getParameter("stringToEncrypt");
        //TODO
        appData.setEncryptString(stringToEncrypt);

        String encryptSmallLetters = stringToEncrypt.toLowerCase();
        String encryptBigLetter = stringToEncrypt.toUpperCase();
        String decryptString = "";
        if(isWordInDictionary(engine,encryptSmallLetters,resourceNameToValueMap)) {
            decryptString = engine.processData(encryptBigLetter, false);
        }

        //TODO
        appData.setDecryptString(decryptString);
        DTOMachineDetails dtoMachineDetails = engine.getMachineDetailsPresenter().createCurrMachineDetails();
        String machineDetails = String.format("%s",engine.showLastMachineDetails(dtoMachineDetails));
        //String jsonEngine = gson.toJson(engine);

        addDataToMap(resourceNameToValueMap, decryptString, machineDetails);

        String json = gson.toJson(resourceNameToValueMap);
        response.getWriter().println(json);
    }
    private void addDataToMap(Map<String, Object> resourceNameToValueMap,  String DecryptString, String machineDetails){
        resourceNameToValueMap.put("DecryptString", DecryptString);
        resourceNameToValueMap.put("machineDetails", machineDetails);

    }

    private boolean isWordInDictionary(Engine currEngine, String word, Map<String,Object> mapRetValuesJson){
        boolean ret = currEngine.getDecryptionManager().getDictionary().isStringInDictionary(word);
        Gson gson = new Gson();
        mapRetValuesJson.put("wordInDictionary",gson.toJson(ret));
        return ret;
    }
}