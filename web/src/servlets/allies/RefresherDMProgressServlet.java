package servlets.allies;

import com.google.gson.Gson;
import dTOUI.DTOActiveAgent;
import dTOUI.DTOAppData;
import dTOUI.DTODmProgress;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RefresherDMProgressServlet", urlPatterns = "/refresherDMProgress")

public class RefresherDMProgressServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();
        String currContestName = request.getParameter("gameTitle");
        String allieName = request.getParameter("allieName");
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());

        List<DTOActiveAgent> agentsDataList = appData.getListOfAgentsData(currContestName,allieName);

        //writing dm progress data
        if(appData.getMapAllieNameToDM().get(allieName) != null){
            long totalMissionExist = appData.getMapAllieNameToDM().get(allieName).getSizeAllMissions();

            long totalMissionsPushedToQ = appData.getMapAllieNameToDM().get(allieName).getMissionDoneUntilNow();

            long missionsDone = 0;
            for(DTOActiveAgent a : agentsDataList){
                missionsDone+= a.getTotalMissions();
            }
            DTODmProgress DMdATA= new DTODmProgress(totalMissionExist,totalMissionsPushedToQ,missionsDone);
            response.getWriter().println(new Gson().toJson(DMdATA));
        }
        else
            response.getWriter().println("");
    }


}