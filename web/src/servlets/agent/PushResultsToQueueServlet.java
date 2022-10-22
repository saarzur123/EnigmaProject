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


@WebServlet(name = "PushResultsToQueueServlet", urlPatterns = "/pushResults")

public class PushResultsToQueueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();
        DTOMissionResult resultToPush = gson.fromJson(request.getParameter("results"),DTOMissionResult.class);
        String teamName = request.getParameter("allieName");
        String contestName = request.getParameter("contestName");

        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        String encryptString = appData.getEncryptString();

        DecryptionManager currDM = appData.getMapAllieNameToDM().get(teamName);

        currDM.pushMissionsToCandidateQueue(resultToPush);
        if(resultToPush.getDecryptString().equals(encryptString)){
            response.getWriter().println("WIN");
        }
        else {
            response.getWriter().println("");
        }
    }
}