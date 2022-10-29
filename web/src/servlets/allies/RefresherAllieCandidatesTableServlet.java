package servlets.allies;

import com.google.gson.Gson;
import dTOUI.DTOAppData;
import decryption.manager.DTOMissionResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "RefresherAllieCandidatesTableServlet", urlPatterns = "/refresherAllieCandidateTable")

public class RefresherAllieCandidatesTableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();
        Map<String, String> resourceNameToValueMap =  new HashMap<>();
        String currContestName = request.getParameter("gameTitle");
        String currAllieName = request.getParameter("allieName");
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());

        List<DTOMissionResult> allieCandidateTableData = appData.getListOfAllieResults(currContestName,currAllieName);

        String mapToJson = gson.toJson(resourceNameToValueMap);
        response.getWriter().println(gson.toJson(allieCandidateTableData));
    }
}