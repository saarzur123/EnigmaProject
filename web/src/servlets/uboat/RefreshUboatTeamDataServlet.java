package servlets.uboat;

import com.google.gson.Gson;
import dTOUI.ActiveTeamsDTO;
import dTOUI.DTOAppData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "RefreshUboatTeamDataServlet", urlPatterns = "/refreshUboatTeamsData")

public class RefreshUboatTeamDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();
        Map<String, String> resourceNameToValueMap =  new HashMap<>();
        Map<String, String> ret =  new HashMap<>();
        String contestName = request.getParameter("gameTitle");
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        if(appData.getMapContestNameToActiveTeamsData().size()>0){
            List<ActiveTeamsDTO> currentContestTeamsList = appData.getMapContestNameToActiveTeamsData().get(contestName);
            for(ActiveTeamsDTO teamData : currentContestTeamsList){
                ret.put(gson.toJson(teamData.getTeamName()),gson.toJson(teamData));
            }
            resourceNameToValueMap.put("teamsDataMap", gson.toJson(ret));
        }

        String mapToJson = gson.toJson(resourceNameToValueMap);
        response.getWriter().println(mapToJson);
    }
}
