package servlets.allies;

import com.google.gson.Gson;
import dTOUI.DTOAppData;
import engine.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SessionUtils;

import java.io.IOException;


@WebServlet(name = "InitializeAlliesServlet", urlPatterns = "/InitializeAlliesServlet")

public class InitializeAlliesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        String gameTitle = request.getParameter("gameTitle");
        String usernameFromSession = SessionUtils.getUsername(request);
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Engine currEngine =  appData.getMapUboatGameTitleToEngineData().get(gameTitle);
        String listToString = gson.toJson(appData.getListFullSContest());

        response.getWriter().println(listToString);
    }
}