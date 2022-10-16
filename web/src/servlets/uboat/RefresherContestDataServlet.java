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


@WebServlet(name = "RefresherContestDataServlet", urlPatterns = "/refresherContestData")

    public class RefresherContestDataServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            response.setContentType("text/plain;charset=UTF-8");
            Gson gson = new Gson();
            Map<String, String> resourceNameToValueMap =  new HashMap<>();
            Map<String, String> retContest =  new HashMap<>();
            Map<String, String> retTeams =  new HashMap<>();
            String currContestName = request.getParameter("gameTitle");
            DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());

            //add all contests data to ret map
            if(appData.getMapContestNameToContestData().size()>0){
                for(String contestName : appData.getMapContestNameToContestData().keySet()){
                    retContest.put(gson.toJson(contestName),gson.toJson(appData.getMapContestNameToContestData().get(contestName)));
                }
                resourceNameToValueMap.put("contestsDataMap", gson.toJson(retContest));
            }

            //add current contest teams data - teamName: activeTeamDTO
            if(appData.getMapContestNameToActiveTeamsData().size()>0){
                List<ActiveTeamsDTO> currentContestTeamsList = appData.getMapContestNameToActiveTeamsData().get(currContestName);
                for(ActiveTeamsDTO teamData : currentContestTeamsList){
                    retTeams.put(gson.toJson(teamData.getTeamName()),gson.toJson(teamData));
                }
                resourceNameToValueMap.put("teamsDataMap", gson.toJson(retTeams));
            }

            String mapToJson = gson.toJson(resourceNameToValueMap);
            response.getWriter().println(mapToJson);
        }
    }
