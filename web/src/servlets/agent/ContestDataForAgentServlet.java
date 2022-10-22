package servlets.agent;

import com.google.gson.Gson;
import dTOUI.ContestDTO;
import dTOUI.DTOAppData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ContestDataForAgentServlet", urlPatterns = "/updateContestData")

public class ContestDataForAgentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();
        String contestName = request.getParameter("contestName");
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Map<String, ContestDTO> contestToStatus = appData.getMapContestNameToContestData();

        ContestDTO contestDTO = contestToStatus.get(contestName);
        String statusJson = gson.toJson(contestDTO);
        response.getWriter().println(statusJson);
    }
}