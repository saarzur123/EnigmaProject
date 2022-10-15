package Uboat.client.util;

import com.google.gson.Gson;

public class Constants {

    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String JHON_DOE = "<Anonymous>";
    public final static int REFRESH_RATE = 2000;
    public final static String CHAT_LINE_FORMATTING = "%tH:%tM:%tS | %.10s: %s%n";

    // fxml locations
    public final static String MAIN_PAGE_FXML_RESOURCE_LOCATION = "/Uboat/client/component/main/UBoat.fxml";
    public final static String LOGIN_PAGE_FXML_RESOURCE_LOCATION = "/Uboat/client/component/login/login.fxml";
    public final static String CHAT_ROOM_FXML_RESOURCE_LOCATION = "/Uboat/client/component/chatroom/chat-room-main.fxml";

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/web_Web_exploded";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;


    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/loginShortResponse";
    public final static String UPLOAD_FILE = LOGIN_PAGE + "/uploadFile";
    public final static String AUTOMATION_SECRET_CODE = LOGIN_PAGE + "/automationCode";

    public final static String ENCRYPT_STRING = LOGIN_PAGE + "/encrypt";
    public final static String RESTART_CODE = LOGIN_PAGE + "/restart";
    public final static String CREATE_USER_SECRET_CODE = LOGIN_PAGE + "/createUserSecretCode";
    public final static String SET_USER_SECRET_CODE = LOGIN_PAGE +"/setUserSecretCode";
    public final static String UBOAT_READY_STATUS = FULL_SERVER_PATH +"/uboatReady";
    public final static String REFRESHER_TEAMS_DATA = FULL_SERVER_PATH +"/refreshUboatTeamsData";
    public final static String ANNOUNCE_UBOAT_READY = FULL_SERVER_PATH + "/uboatLetKnowIfReady";
    public final static String AUTO_SECRET_CODE = FULL_SERVER_PATH + "/autoSecretCode";
    public final static String USERS_LIST = FULL_SERVER_PATH + "/userslist";
    public final static String LOGOUT = FULL_SERVER_PATH + "/chat/logout";
    public final static String SEND_CHAT_LINE = FULL_SERVER_PATH + "/pages/chatroom/sendChat";
    public final static String CHAT_LINES_LIST = FULL_SERVER_PATH + "/chat";

    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();
}
