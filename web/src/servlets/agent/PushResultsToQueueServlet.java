package servlets.agent;

import com.google.gson.Gson;
import dTOUI.DTOAppData;
import decryption.manager.DTOMissionResult;
import decryption.manager.DecryptionManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "PushResultsToQueueServlet", urlPatterns = "/pushResults")

public class PushResultsToQueueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();
        Map<String,String> retJson = new HashMap<>();

        DTOMissionResult resultToPush = gson.fromJson(request.getParameter("results"),DTOMissionResult.class);
        String teamName = request.getParameter("allieName");
        String contestName = request.getParameter("contestName");

        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        String encryptString = appData.getEncryptString();//search for

        DecryptionManager currDM = appData.getMapAllieNameToDM().get(teamName);

        //check if there are candidates
        if(resultToPush.getEncryptionCandidates().size()>0){
            retJson.put("results",gson.toJson(resultToPush));
            currDM.pushMissionsToCandidateQueue(resultToPush);
                String stringRes = resultToPush.getDecryptString().toUpperCase();
                //in case of winning
                if(stringRes.equals(encryptString.toUpperCase())){
                    retJson.put("status","WIN");
                    //contest over - contest status changed and allies status changed
                    appData.getMapContestNameToContestReadyStatus().put(contestName,false);
                    List<String> listOfReadyAllies = appData.getListOfReadyAlliesByContestName(contestName);
                    Map<String, DecryptionManager> mapAllieNameToDM = appData.getMapAllieNameToDM();
                    for(String allieName: mapAllieNameToDM.keySet()){
                        if(allieName!=teamName) {
                            //DecryptionManager DM = mapAllieNameToDM.get(allieName);
                            //DM.showLosePopup("LOSER");
                        }
                    }
                }
            }else {
            retJson.put("status","");
        }
        response.getWriter().println(gson.toJson(retJson));
        }

    }
