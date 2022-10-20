package servlets.agent;

import dTOUI.ActiveTeamsDTO;
import dTOUI.DTOAppData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "GetAgentMissionServlet", urlPatterns = "/getAgentMission")

public class GetAgentMissionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Map<String, ActiveTeamsDTO> stringAlliesDTOMap = appData.getMapTeamNameAllActiveTeamsData();
        ActiveTeamsDTO active = stringAlliesDTOMap.remove(request.getParameter("alliesName"));
        active.setAgentNumberInt(active.getAgentNumberInt() + 1);
        stringAlliesDTOMap.put(active.getTeamName(), active);
        //searchInMapActiveTeam(request.getParameter("alliesName"), appData);

    }
}