package servlets.allies;

import com.google.gson.Gson;
import dTOUI.ActiveTeamsDTO;
import dTOUI.DTOAppData;
import engine.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


    @WebServlet(name = "AllieConfigureIsReadyServlet", urlPatterns = "/AllieConfigureIsReady")

    public class AllieConfigureIsReadyServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/plain;charset=UTF-8");

            Gson gson = new Gson();
            Map<String, String> resourceNameToValueMap = new HashMap<>();
            String newAlliesActiveTeamAddedJson = request.getParameter("teamDTO");
            String chosenContest = request.getParameter("contestName");
            ActiveTeamsDTO teamsDTO = gson.fromJson(newAlliesActiveTeamAddedJson,ActiveTeamsDTO.class);
            DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
            appData.addToMapContestNameToActiveTeamsData(teamsDTO,chosenContest);

            //adding new dm
            Engine currEngine = appData.getMapUboatGameTitleToEngineData().get(chosenContest);
            appData.addDMToMap(teamsDTO.getTeamName(),currEngine.getDM());

            ActiveTeamsDTO[] activeTeamsData = appData.getMapContestNameToActiveTeamsData().get(chosenContest).toArray(new ActiveTeamsDTO[0]);
            resourceNameToValueMap.put("listTeams", gson.toJson(activeTeamsData));
            resourceNameToValueMap.put("updatedContestData",gson.toJson(appData.getMapContestNameToContestData().get(chosenContest)));

            response.getWriter().println(gson.toJson(resourceNameToValueMap));
        }


}
