package servlets.uboat;

import com.google.gson.Gson;
import dTOUI.DTOAppData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RefreshUboatTeamDataServlet", urlPatterns = "/refreshUboatTeamsData")

public class RefreshUboatTeamDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();
        Map<String, String> resourceNameToValueMap =  new HashMap<>();
        Map<String, String> ret =  new HashMap<>();

        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        if(appData.getMapTeamNameToActiveTeamsData().size()>0){
            for(String teamName : appData.getMapTeamNameToActiveTeamsData().keySet()){
                ret.put(gson.toJson(teamName),gson.toJson(appData.getMapContestNameToContestData().get(teamName)));
            }
            resourceNameToValueMap.put("teamsDataMap", gson.toJson(ret));
        }

        String mapToJson = gson.toJson(resourceNameToValueMap);
        response.getWriter().println(mapToJson);
    }
}
