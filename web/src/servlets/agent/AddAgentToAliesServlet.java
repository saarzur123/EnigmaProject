package servlets.agent;

import dTOUI.ActiveTeamsDTO;
import dTOUI.DTOAppData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AddAgentToAliesServlet", urlPatterns = "/addAgentToAllies")

public class AddAgentToAliesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Map<String, ActiveTeamsDTO> stringAlliesDTOMap = appData.getMapTeamNameAllActiveTeamsData();
        ActiveTeamsDTO active = stringAlliesDTOMap.remove(request.getParameter("alliesName"));
        active.setAgentNumberInt(active.getAgentNumberInt() + 1);
        stringAlliesDTOMap.put(active.getTeamName(), active);
        searchInMapActiveTeam(request.getParameter("alliesName") , appData);

    }
    private void searchInMapActiveTeam(String alliesName, DTOAppData appData){
        Map<String, List<ActiveTeamsDTO>> contestNameToListActiveTeam = appData.getMapContestNameToActiveTeamsData();

        for(String contestName : contestNameToListActiveTeam.keySet()){
            List<ActiveTeamsDTO> listActiveTeam = contestNameToListActiveTeam.get(contestName);
            for (int i = 0; i < listActiveTeam.size(); i++) {
                if(listActiveTeam.get(i).getTeamName() == alliesName){
                    listActiveTeam.get(i).setAgentNumber(listActiveTeam.get(i).getAgentNumber() + 1);
                    i = listActiveTeam.size();
                    break;
                }
            }
        }
    }
}
