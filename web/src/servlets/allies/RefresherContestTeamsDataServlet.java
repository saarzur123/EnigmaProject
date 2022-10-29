package servlets.allies;

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

@WebServlet(name = "RefresherContestTeamsDataServlet", urlPatterns = "/refresherContestTeamsData")

public class RefresherContestTeamsDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();
        Map<String, String> resourceNameToValueMap =  new HashMap<>();
        Map<String, String> retTeams =  new HashMap<>();
        String currContestName = request.getParameter("gameTitle");
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());

        Map<String, ActiveTeamsDTO> stringAlliesDTOMap = appData.getMapTeamNameAllActiveTeamsData();
        ActiveTeamsDTO active = stringAlliesDTOMap.get(request.getParameter("alliesName"));
//        if(active.getAgentNumberInt() > 0)
//            resourceNameToValueMap.put("agentEntered", "YES");

        //add current contest teams data - teamName: activeTeamDTO
        if(appData.getMapContestNameToActiveTeamsData().size()>0) {
            List<ActiveTeamsDTO> currentContestTeamsList = appData.getMapContestNameToActiveTeamsData().get(currContestName);
            if (!currentContestTeamsList.isEmpty()){
                for (ActiveTeamsDTO teamData : currentContestTeamsList) {
                    retTeams.put(gson.toJson(teamData.getTeamName()), gson.toJson(teamData));
                }
            resourceNameToValueMap.put("teamsDataMap", gson.toJson(retTeams));
        }
        }
        if(appData.getMapAlliesNameToReadyToStart().containsKey(request.getParameter("alliesName")))
            resourceNameToValueMap.put("ready", "ready");

        String mapToJson = gson.toJson(resourceNameToValueMap);
        response.getWriter().println(mapToJson);
    }
}