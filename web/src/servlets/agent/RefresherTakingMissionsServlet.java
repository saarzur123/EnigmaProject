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
import java.util.*;

@WebServlet(name = "RefresherTakingMissionsServlet", urlPatterns = "/refresherTakingMissions")

public class RefresherTakingMissionsServlet extends HttpServlet {
    private Set<Integer> missionIds = new HashSet<>();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Map<String,String> retValues = new HashMap<>();
       //commit List<Mission> missionsPackage = new ArrayList<>();
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
                retValues.put("listMissions",new Gson().toJson(missionsPackage));

                //commit List<Mission> missionsPackageTest = currentDM.returnMissionPackage(packageAmount);
               //commit checkIfAlreadyInAndUpdate(missionsPackageTest,missionsPackage);
            } catch (InterruptedException e) {
                System.out.println("EXCEPTION IN REFRESHER MISSIONS SERVLET");
            }
        }
       //commit retValues.put("listMissions",new Gson().toJson(missionsPackage));
        response.getWriter().println(new Gson().toJson(retValues));
    }

    private void checkIfAlreadyInAndUpdate(List<Mission> missionsPackageTest,List<Mission> missionsPackage){
        for(Mission m : missionsPackageTest){
            if(!missionIds.contains(m.getId())){
                missionIds.add(m.getId());
                missionsPackage.add(m);
            }
        }
    }

}