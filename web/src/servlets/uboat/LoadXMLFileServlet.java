package servlets.uboat;

import com.google.gson.Gson;
import dTOUI.DTOAppData;
import dTOUI.DTOMachineDetails;
import engine.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import uboat.engine.battleField.BattleFieldManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@WebServlet(name = "LoadXMLFileServlet", urlPatterns = {"/loginShortResponse/uploadFile"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class LoadXMLFileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        Map<String,String> resourceNameToValueMap =  new HashMap<>();

        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        BattleFieldManager battleFieldManager = utils.ServletUtils.getBattleFieldManager(getServletContext());

        Engine currEngine = new Engine();
        String fileContent = getFileContent(request).toString();
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));
        currEngine.createMachineFromXML(inputStream);
        DTOMachineDetails dtoMachineDetails = currEngine.getMachineDetailsPresenter().createCurrMachineDetails();
        String machineDetails = String.format("%s",currEngine.showLastMachineDetails(dtoMachineDetails));
        String isUniqueGameTitle = validateUniqueGameTitle(currEngine.getBattleField().getGameTitle(),battleFieldManager);

        //adding new contest:engine pair if the game title is valid
        addUboatContestAndEngine(appData,currEngine,isUniqueGameTitle);

        //deliver essential data back to ui
        addingDataToMap(resourceNameToValueMap,currEngine.getBattleField().getGameTitle(),machineDetails,isUniqueGameTitle);

        String json = gson.toJson(resourceNameToValueMap);
        response.getWriter().println(json);
    }

    private void  addingDataToMap(Map<String,String> dest, String gameTitle, String machineDetails, String uniqueGameTitle){
        dest.put("gameTitle",gameTitle);
        dest.put("machineDetails",machineDetails);
        dest.put("uniqueGameTitle",uniqueGameTitle);
    }

    private void addUboatContestAndEngine(DTOAppData appData, Engine currEngine, String isUniqueGameTitle){
        if(isUniqueGameTitle == "ok"){
            appData.addToMapUboatGameTitleToEngineData(currEngine.getBattleField().getGameTitle(), currEngine);
        }
    }

    private String validateUniqueGameTitle(String gameTitle, BattleFieldManager battleFieldManager){
        if(battleFieldManager.getGameTitles().contains(gameTitle)){
            return gameTitle;
        }
        battleFieldManager.addGameTitle(gameTitle);
        return "ok";
    }

    private StringBuilder getFileContent(HttpServletRequest request) throws ServletException, IOException {
        Collection<Part> parts = request.getParts();

      //  out.println("Total parts : " + parts.size());

        StringBuilder fileContent = new StringBuilder();

        for (Part part : parts) {
            //printPart(part, out);//to write the content of the file to a string
            fileContent.append(readFromInputStream(part.getInputStream()));
        }
        return  fileContent;
    }

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }

}