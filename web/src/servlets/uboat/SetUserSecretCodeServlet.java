package servlets.uboat;

import com.google.gson.Gson;
import dTOUI.DTOAppData;
import dTOUI.DTOMachineDetails;
import dTOUI.DTOSecretCodeFromUser;
import engine.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

    @WebServlet(name = "SetUserSecretCodeServlet", urlPatterns = "/loginShortResponse/setUserSecretCode")

    public class SetUserSecretCodeServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/plain;charset=UTF-8");
            Gson gson = new Gson();
            Map<String, String> resourceNameToValueMap = new HashMap<>();

            //get engine
            String gameTitle = request.getParameter("gameTitle");
            DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
            Engine currEngine =  appData.getMapUboatGameTitleToEngineData().get(gameTitle);

            //get user secret code configuration - user dto
            String secretCodeConfigureJson = request.getParameter("userSecretCodeConfigureDto");
            DTOSecretCodeFromUser userDto = gson.fromJson(secretCodeConfigureJson,DTOSecretCodeFromUser.class);
            currEngine.getSecretCodeFromUser(userDto,false);

            //update machine details
            DTOMachineDetails dtoMachineDetails = currEngine.getMachineDetailsPresenter().createCurrMachineDetails();
            String machineDetails = String.format("%s",currEngine.showLastMachineDetails(dtoMachineDetails));

            resourceNameToValueMap.put("machineDetails",machineDetails);
            resourceNameToValueMap.put("secretCode", currEngine.getSecretCode().getSecretCodeCombination());

            String json = gson.toJson(resourceNameToValueMap);
            response.getWriter().println(json);
        }
}
