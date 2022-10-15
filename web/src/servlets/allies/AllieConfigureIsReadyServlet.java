package servlets.allies;

import com.google.gson.Gson;
import dTOUI.ActiveTeamsDTO;
import dTOUI.DTOAppData;
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
            ActiveTeamsDTO teamsDTO = gson.fromJson(newAlliesActiveTeamAddedJson,ActiveTeamsDTO.class);
            DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
            appData.addToMapTeamNameToActiveTeamsData(teamsDTO);

            ActiveTeamsDTO[] activeTeamsData = appData.getMapTeamNameToActiveTeamsData().values().toArray(new ActiveTeamsDTO[0]);
            resourceNameToValueMap.put("listTeams", gson.toJson(activeTeamsData));

            response.getWriter().println(gson.toJson(resourceNameToValueMap));
        }
}
