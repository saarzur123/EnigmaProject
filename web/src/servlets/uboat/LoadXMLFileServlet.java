package servlets.uboat;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uboat.engine.users.UserManager;
import utils.SessionUtils;

import java.io.IOException;

@WebServlet(name = "LoadXMLFileServlet", urlPatterns = {"/loginShortResponse/uploadFile"})
public class LoadXMLFileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        String usernameFromSession = SessionUtils.getUsername(request);
        UserManager userManager = chat.utils.ServletUtils.getUserManager(getServletContext());
    }
