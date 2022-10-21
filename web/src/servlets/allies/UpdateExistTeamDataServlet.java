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

@WebServlet(name = "UpdateExistTeamDataServlet", urlPatterns = "/AllieUpdateExistTeamData")

public class UpdateExistTeamDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Gson gson = new Gson();
        Map<String, String> resourceNameToValueMap = new HashMap<>();
        String newAlliesActiveTeamAddedJson = request.getParameter("teamDTO");
        String chosenContest = request.getParameter("contestName");
        ActiveTeamsDTO teamsDTO = gson.fromJson(newAlliesActiveTeamAddedJson, ActiveTeamsDTO.class);
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());

        appData.removeFromMapContestNameToActiveTeamsData(chosenContest,teamsDTO.getTeamName());
        appData.addToMapContestNameToActiveTeamsData(teamsDTO, chosenContest);

        appData.removeFromMapTeamNameAllActiveTeamsData(teamsDTO.getTeamName());
        appData.addToMapTeamNameAllActiveTeamsData(teamsDTO);

        response.getWriter().println(gson.toJson(resourceNameToValueMap));
    }


}