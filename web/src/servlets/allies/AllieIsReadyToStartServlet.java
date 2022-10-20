package servlets.allies;

import com.google.gson.Gson;
import dTOUI.ActiveTeamsDTO;
import dTOUI.DTOAppData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "AllieIsReadyToStartServlet", urlPatterns = "/allieIsReadyToStart")

public class AllieIsReadyToStartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Gson gson = new Gson();
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        String newAlliesActiveTeamAddedJson = request.getParameter("teamDTO");
        ActiveTeamsDTO teamsDTO = gson.fromJson(newAlliesActiveTeamAddedJson,ActiveTeamsDTO.class);
        //ADD ALLIES NAME TO READY LIST
        appData.getListOfReadyAllies().add(teamsDTO.getTeamName());
    }
}
