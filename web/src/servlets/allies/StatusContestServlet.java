package servlets.allies;

import com.google.gson.Gson;
import dTOUI.ActiveTeamsDTO;
import dTOUI.DTOAppData;
import decryption.manager.DecryptionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "StatusContestServlet", urlPatterns = "/refreshContestStatus")

public class StatusContestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain;charset=UTF-8");
        String contestName = request.getParameter("contestName");
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        checkIfContestStartAndUpdateInMap(appData, contestName);

    }
    private void checkIfContestStartAndUpdateInMap(DTOAppData appData, String contestName){
        Map<String, List<ActiveTeamsDTO>> contestNameToActiveTeams = appData.getMapContestNameToActiveTeamsData();
        List<ActiveTeamsDTO> listActiveTeam = contestNameToActiveTeams.get(contestName);
        int counter = 0;
        for (int i = 0; i < listActiveTeam.size(); i++) {
            ActiveTeamsDTO activeTeam = listActiveTeam.get(i);
            if(appData.getListOfReadyAllies().contains(activeTeam.getTeamName())) {
                counter++;
            }
        }
        if(counter == listActiveTeam.size()) {
            appData.getMapContestNameToContestReadyStatus().put(contestName, true);
            Map<String, DecryptionManager> mapAlliesNameToDM = appData.getMapAllieNameToDM();
            for (int i = 0; i < listActiveTeam.size(); i++) {
                DecryptionManager DM = mapAlliesNameToDM.get(listActiveTeam.get(i).getTeamName());
                //TODO CONSUMER THAT UPDATE UI
                DM.findSecretCode(appData.getEncryptString(), appData.getMapContestNameToContestData().get(contestName).getCompetitionLevel(),null,null,null);
            }
        }
        else appData.getMapContestNameToContestReadyStatus().put(contestName, false);
    }
}
