package servlets.uboat;

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
import utils.SessionUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;

@WebServlet(name = "LoadXMLFileServlet", urlPatterns = {"/loginShortResponse/uploadFile"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class LoadXMLFileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain");
        String usernameFromSession = SessionUtils.getUsername(request);
        DTOAppData appData = utils.ServletUtils.getDTOAppData(getServletContext());
        appData.addToMapUboatUsernameToEngineData(usernameFromSession, new Engine());

        Engine currEngine = appData.getMapUboatToEngineData().get(usernameFromSession);
        String fileContent = getFileContent(request).toString();
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));
        currEngine.createMachineFromXML(inputStream);
        DTOMachineDetails dtoMachineDetails = currEngine.getMachineDetailsPresenter().createCurrMachineDetails();
        String machineDetails = String.format("%s",currEngine.showLastMachineDetails(dtoMachineDetails));
        response.getWriter().println(machineDetails);
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