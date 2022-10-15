package servlets.uboat;

import battleField.BattleField;
import com.google.gson.Gson;
import dTOUI.ContestDTO;
import dTOUI.DTOAppData;
import engine.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SessionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RefreshExistsUboat", urlPatterns = "/refreshExistsUboat")

public class RefreshExistsUboatServlet extends HttpServlet {
    //            Engine currEngine =  appData.getMapUboatGameTitleToEngineData().get(gameTitle);
//            BattleField contestData = currEngine.getBattleField();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        Map<String,String> resourceNameToValueMap =  new HashMap<>();
        String gameTitle = request.getParameter("gameTitle");
        String usernameFromSession = SessionUtils.getUsername(request);
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Engine currEngine =  appData.getMapUboatGameTitleToEngineData().get(gameTitle);
        BattleField contestData = currEngine.getBattleField();
        contestData.setCurrAmountOfAllies(contestData.getCurrAmountOfAllies() + 1 );


        contestData.setUserNameOfContestCreator(usernameFromSession);
        ContestDTO contestDTO = new ContestDTO(gameTitle, contestData.getCompetitionLevel(), usernameFromSession,contestData.getCurrAmountOfAllies(),contestData.getAlliesAmount(),false);
        appData.updateExistsUboat(contestDTO);
        Map<String,ContestDTO> contestMap =  appData.getMapContestNameToContestData();
        boolean full = false;
        if(contestDTO.getAlliesAmountEntered() == contestDTO.getAlliesAmountNeeded()){
            full = true;
        }
        Map<String,String> map = new HashMap<>();
        String mapToString = gson.toJson(contestMap);
        addValueToMap(map, mapToString, full);
        String json = gson.toJson(map);

        response.getWriter().println(json);
    }
    public void addValueToMap(Map<String,String> map,String contestMap, boolean full ){
        map.put("map", contestMap);
        if(full)
            map.put("full", "YES");
        else map.put("full", "NO");

    }

}