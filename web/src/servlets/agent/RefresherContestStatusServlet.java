package servlets.agent;

import com.google.gson.Gson;
import dTOUI.DTOAppData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "RefresherContestStatusServlet", urlPatterns = "/refresherContestStatus")

public class RefresherContestStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        String contestName = request.getParameter("gameTitle");
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Map<String, Boolean> contestToStatus = appData.getMapContestNameToContestReadyStatus();

        boolean status = contestToStatus.get(contestName);

        response.getWriter().println(new Gson().toJson(status));
    }
}