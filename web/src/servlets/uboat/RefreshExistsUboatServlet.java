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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        String gameTitle = request.getParameter("gameTitle");
        String usernameFromSession = SessionUtils.getUsername(request);
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Engine currEngine =  appData.getMapUboatGameTitleToEngineData().get(gameTitle);
        BattleField contestData = currEngine.getBattleField();
        contestData.setCurrAmountOfAllies(contestData.getCurrAmountOfAllies() + 1 );


        contestData.setUserNameOfContestCreator(usernameFromSession);
        ContestDTO contestDTO = new ContestDTO(gameTitle, contestData.getCompetitionLevel(), usernameFromSession,contestData.getCurrAmountOfAllies(),contestData.getAlliesAmount(),false);
        //update new data in app data
        appData.updateExistsUboat(contestDTO);

        Map<String,ContestDTO> contestMap =  appData.getMapContestNameToContestData();
        boolean full = false;
        if(contestDTO.getAlliesAmountEntered() == contestDTO.getAlliesAmountNeeded()){
            full = true;
        }

        //adding contest map as jason string and boolean value to retMap
        Map<String,String> retMap = new HashMap<>();
        Map<String,String> mapContestNameToContestDtoJson = new HashMap<>();
        for(String contestName : contestMap.keySet()){
            mapContestNameToContestDtoJson.put(gson.toJson(contestName),gson.toJson(contestMap.get(contestName)));
        }
        String mapToString = gson.toJson(mapContestNameToContestDtoJson);
        addValueToMap(retMap, mapToString, full);

        //returning values map as jason string
        String jsonRetMap = gson.toJson(retMap);
        response.getWriter().println(jsonRetMap);
    }
    public void addValueToMap(Map<String,String> map,String contestMap, boolean full ){
        map.put("map", contestMap);
        if(full)
            map.put("full", "YES");
        else map.put("full", "NO");

    }

}