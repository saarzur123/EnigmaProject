package servlets.allies;

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
            if(appData.getListOfReadyAlliesByContestName(contestName).contains(activeTeam.getTeamName())) {
                counter++;
            }
        }
        if(counter == listActiveTeam.size()) {
            Map<String, DecryptionManager> mapAlliesNameToDM = appData.getMapAllieNameToDM();

            //check if contest didn't start already - prevent multiple missions
            if(!appData.getMapContestNameToContestReadyStatus().get(contestName)) {
               //activate competition
                for (int i = 0; i < listActiveTeam.size(); i++) {
                    DecryptionManager DM = mapAlliesNameToDM.get(listActiveTeam.get(i).getTeamName());
                    DM.findSecretCode(appData.getDecryptString(), appData.getMapContestNameToContestData().get(contestName).getCompetitionLevel(), null, null, null);
                }
                //change contest status - start
                appData.getMapContestNameToContestReadyStatus().put(contestName, true);
            }
        }
        else appData.getMapContestNameToContestReadyStatus().put(contestName, false);
    }
}
