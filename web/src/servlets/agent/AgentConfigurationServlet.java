package servlets.agent;

import com.google.gson.Gson;
import dTOUI.ActiveTeamsDTO;
import dTOUI.DTOAppData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AgentConfigurationServlet", urlPatterns = "/configurationAgent")

public class AgentConfigurationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Gson gson = new Gson();
        Map<String, String> resourceNameToValueMap = new HashMap<>();
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Map<String, ActiveTeamsDTO> teamsMap = appData.getMapTeamNameAllActiveTeamsData();
        List<String> retListTeamsNames = new ArrayList<>();

        for(String teamName : teamsMap.keySet()){
            retListTeamsNames.add(teamName);
        }

        resourceNameToValueMap.put("teamsNamesList", gson.toJson(retListTeamsNames));

        response.getWriter().println(gson.toJson(resourceNameToValueMap));
    }
}