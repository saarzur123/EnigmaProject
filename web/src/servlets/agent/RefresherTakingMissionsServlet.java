package servlets.agent;

import com.google.gson.Gson;
import dTOUI.DTOAppData;
import dTOUI.MissionDTO;
import decryption.manager.DecryptionManager;
import decryption.manager.Mission;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "RefresherTakingMissionsServlet", urlPatterns = "/refresherTakingMissions")

public class RefresherTakingMissionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Map<String,String> retValues = new HashMap<>();
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        Map<String, DecryptionManager> alliesToDM = appData.getMapAllieNameToDM();

        DecryptionManager currentDM = alliesToDM.get(request.getParameter("teamName"));
        int packageAmount = new Gson().fromJson(request.getParameter("packageAmount"),Integer.class);

        //TODO CHECK IF NEED TO BE SYNCHRONIZED
        boolean isAllMissionsOut = currentDM.checkIfDoneCreatingMissions();
        retValues.put("isAllMissionsOut",new Gson().toJson(isAllMissionsOut));

        if(!isAllMissionsOut){
            try {
                Gson gson = new Gson();
                List<Mission> missionsPackage = currentDM.returnMissionPackage(packageAmount);
//                List<MissionDTO> missionsData = createMissionsData(missionsPackage);
//                List<String> retListMissionsData = new ArrayList<>();
//                for (int i = 0; i < missionsData.size(); i++) {
//                    MissionDTO mission = missionsData.get(i);
//                    String stringMissionJson = gson.toJson(mission);
//                    retListMissionsData.add(stringMissionJson);
//                }
                retValues.put("listMissions",new Gson().toJson(missionsPackage));
            } catch (InterruptedException e) {
                System.out.println("EXCEPTION IN REFRESHER MISSIONS SERVLET");
            }
        }
        response.getWriter().println(new Gson().toJson(retValues));
    }

    private List<MissionDTO> createMissionsData(List<Mission> missionsPackage){
        List<MissionDTO> retList = new ArrayList<>();
        for(Mission mission : missionsPackage){
          //  MissionDTO newMissionData = new MissionDTO(mission.getMissionArguments(),mission.getUserDecryptedString(), mission.getStartIndexes());
            // retList.add(newMissionData);
        }
        return retList;
    }
}