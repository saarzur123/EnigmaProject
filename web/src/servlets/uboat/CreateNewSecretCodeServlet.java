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


@WebServlet(name = "CreateNewSecretCodeServlet", urlPatterns = "/loginShortResponse/createUserSecretCode")

public class CreateNewSecretCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Gson gson = new Gson();
        Map<String,String> resourceNameToValueMap =  new HashMap<>();
        String gameTitle = request.getParameter("gameTitle");
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Engine currEngine =  appData.getMapUboatGameTitleToEngineData().get(gameTitle);

        //collect data that will get back to ui
        int inUseRotor = currEngine.getMachine().getInUseRotorNumber();
        String ABC= currEngine.getMachine().getABC();
        int reflectorsNum = currEngine.getMachine().getAvailableReflectors().size();
        int availableRotorNum = currEngine.getMachine().getAvailableRotors().size();

        DTOMachineDetails dtoMachineDetails = currEngine.getMachineDetailsPresenter().createCurrMachineDetails();
        String machineDetails = String.format("%s",currEngine.showLastMachineDetails(dtoMachineDetails));

        addDataToMap(resourceNameToValueMap,machineDetails,inUseRotor,ABC,reflectorsNum,availableRotorNum);

        String json = gson.toJson(resourceNameToValueMap);
        response.getWriter().println(json);
    }

    private void addDataToMap(Map<String,String> dest, String machineDetails, int inUseRotor,String ABC, int reflectorsNum, int availableRotorNum){
        String inUseRotorStr = String.valueOf(inUseRotor);
        String reflectorsNumStr = String.valueOf(reflectorsNum);
        String availableRotorNumStr = String.valueOf(availableRotorNum);

        dest.put("machineDetails",machineDetails);
        dest.put("inUseRotor",inUseRotorStr);
        dest.put("machineABC",ABC);
        dest.put("totalReflectorsNumber",reflectorsNumStr);
        dest.put("availableRotorsNumber",availableRotorNumStr);

    }
}
