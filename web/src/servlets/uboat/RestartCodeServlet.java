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

@WebServlet(name = "RestartCodeServlet", urlPatterns = "/loginShortResponse/restart")

public class RestartCodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String gameTitle = request.getParameter("gameTitle");
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Map<String, Engine> map = appData.getMapUboatGameTitleToEngineData();
        Engine engine = map.get(gameTitle);
        engine.validateUserChoiceAndResetSecretCode();
        DTOMachineDetails dtoMachineDetails = engine.getMachineDetailsPresenter().createCurrMachineDetails();
        String machineDetails = String.format("%s",engine.showLastMachineDetails(dtoMachineDetails));

        Map<String,String> resourceNameToValueMap =  new HashMap<>();
        addDataToMap(resourceNameToValueMap, machineDetails);

        String json = gson.toJson(resourceNameToValueMap);
        response.getWriter().println(json);
    }
    private void addDataToMap(Map<String, String> resourceNameToValueMap, String machineDetails){
        resourceNameToValueMap.put("machineDetails", machineDetails);

    }

}
