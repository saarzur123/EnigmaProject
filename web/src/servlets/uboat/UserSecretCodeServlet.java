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


@WebServlet(name = "UserSecretCodeServlet", urlPatterns = "/loginShortResponse/userSecretCode")
public class UserSecretCodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Map<String, String> resourceNameToValueMap = new HashMap<>();

        String gameTitle = request.getParameter("gameTitle");
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Map<String, Engine> map = appData.getMapUboatGameTitleToEngineData();
        Engine engine = map.get(gameTitle);

        engine.getRandomSecretCode();

        //updating machine details with new code
        DTOMachineDetails dtoMachineDetails = engine.getMachineDetailsPresenter().createCurrMachineDetails();
        String machineDetails = String.format("%s", engine.showLastMachineDetails(dtoMachineDetails));

        //adding data values to get back from response
        addDataToMap(resourceNameToValueMap, engine.getSecretCode().getSecretCodeCombination(), machineDetails);

        String json = gson.toJson(resourceNameToValueMap);
        response.getWriter().println(json);
    }

    private void addDataToMap(Map<String, String> resourceNameToValueMap, String secretCode, String machineDetails) {
        resourceNameToValueMap.put("secretCode", secretCode);
        resourceNameToValueMap.put("machineDetails", machineDetails);

    }
}
