package servlets.agent;

import com.google.gson.Gson;
import dTOUI.DTOAppData;
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
                List<Mission> missionsPackage = currentDM.returnMissionPackage(packageAmount);
                List<String> retList = new ArrayList<>();
                for (int i = 0; i < missionsPackage.size(); i++) {
                    Mission mission = missionsPackage.get(i);
                    String stringMission = new Gson().toJson(mission);
                    retList.add(stringMission);
                }
                retValues.put("listMissions",new Gson().toJson(missionsPackage));
            } catch (InterruptedException e) {
                System.out.println("EXCEPTION IN REFRESHER MISSIONS SERVLET");
            }
        }
        response.getWriter().println(new Gson().toJson(retValues));
    }
}