package servlets.allies;

import com.google.gson.Gson;
import dTOUI.DTOActiveAgent;
import dTOUI.DTOAppData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "RefresherUpdateAgentsDataAreaServlet", urlPatterns = "/refresherAgentsDataArea")

public class RefresherUpdateAgentsDataAreaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();
        String currContestName = request.getParameter("gameTitle");
        String allieName = request.getParameter("allieName");
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());

        List<DTOActiveAgent> agentsDataList = appData.getListOfAgentsData(currContestName,allieName);

        String agentDataListJson = gson.toJson(agentsDataList);
        response.getWriter().println(agentDataListJson);
    }
}