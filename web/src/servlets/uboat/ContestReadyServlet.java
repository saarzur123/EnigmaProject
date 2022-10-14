package servlets.uboat;

import battleField.BattleField;
import com.google.gson.Gson;
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


@WebServlet(name = "ContestReadyServlet", urlPatterns = "/contestReady")

    public class ContestReadyServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/plain;charset=UTF-8");

            Gson gson = new Gson();
            Map<String,String> resourceNameToValueMap =  new HashMap<>();
            String gameTitle = request.getParameter("gameTitle");
            String usernameFromSession = SessionUtils.getUsername(request);
            DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
            Engine currEngine =  appData.getMapUboatGameTitleToEngineData().get(gameTitle);
            BattleField contestData = currEngine.getBattleField();

            //update curr contest data
            contestData.setUboatButtonReady(true);
            contestData.setUserNameOfContestCreator(usernameFromSession);


        }
}
