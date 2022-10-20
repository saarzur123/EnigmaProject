package servlets.agent;

import dTOUI.ActiveTeamsDTO;
import dTOUI.DTOAppData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "RefresherAgentContestNameServlet", urlPatterns = "/refresherAgentContestName")

public class RefresherAgentContestNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Map<String, ActiveTeamsDTO> teamNameToTeamDTO = appData.getMapTeamNameAllActiveTeamsData();
        ActiveTeamsDTO teamDTO = teamNameToTeamDTO.remove(request.getParameter("teamName"));

        //print contest name if exist and "" if not
        response.getWriter().println(teamDTO.getContestName());
    }
}