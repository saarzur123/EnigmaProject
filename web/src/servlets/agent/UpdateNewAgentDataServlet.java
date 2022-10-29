package servlets.agent;

import com.google.gson.Gson;
import dTOUI.DTOActiveAgent;
import dTOUI.DTOAppData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "UpdateNewAgentDataServlet", urlPatterns = "/addAgentDataToMap")

public class UpdateNewAgentDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();

        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        String allieName = request.getParameter("allieName");
        String contestName = request.getParameter("contestName");
        DTOActiveAgent agentData = gson.fromJson(request.getParameter("agentData"), DTOActiveAgent.class);

        List<DTOActiveAgent> agentsList = appData.getListOfAgentsData(contestName, allieName);

        for (DTOActiveAgent a : agentsList) {
            if (a.getAgentName().equals(agentData.getAgentName())) {
                agentsList.remove(a);
            }
        }

        agentsList.add(agentData);
        response.getWriter().println("hihihi");
    }
}