package util;

import com.google.gson.Gson;

public class ConstantsAG {

    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String JHON_DOE = "<Anonymous>";
    public final static int REFRESH_RATE = 2000;
    public final static String CHAT_LINE_FORMATTING = "%tH:%tM:%tS | %.10s: %s%n";

    // fxml locations
    public final static String MAIN_PAGE_FXML_RESOURCE_LOCATION = "/component/main/app/MainAppAllie.fxml";
    public final static String LOGIN_PAGE_FXML_RESOURCE_LOCATION = "/UBoat/src/Uboat/client/component/login/login.fxml";
    public final static String CHAT_ROOM_FXML_RESOURCE_LOCATION = "/Uboat/client/component/chatroom/chat-room-main.fxml";

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/web_Web_exploded";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;
    public final static String CONFIGURATION_AGENT = FULL_SERVER_PATH +"/configurationAgent";
    public final static String REFRESH_CONTEST_NAME = FULL_SERVER_PATH +"/refresherAgentContestName";
    public final static String REFRESH_CONTEST_STATUS = FULL_SERVER_PATH +"/refresherContestStatus";
    public final static String REFRESH_TAKING_MISSIONS = FULL_SERVER_PATH +"/refresherTakingMissions";
    public final static String ADD_AGENT_TO_ALIES = FULL_SERVER_PATH +"/addAgentToAllies";
    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/loginShortResponse";
    public final static String UPLOAD_FILE = LOGIN_PAGE + "/uploadFile";
    public final static String AUTOMATION_SECRET_CODE = LOGIN_PAGE + "/automationCode";
    public final static String ENCRYPT_STRING = LOGIN_PAGE + "/encrypt";
    public final static String RESTART_CODE = LOGIN_PAGE + "/restart";
    public final static String CREATE_USER_SECRET_CODE = LOGIN_PAGE + "/createUserSecretCode";
    public final static String SET_USER_SECRET_CODE = LOGIN_PAGE +"/setUserSecretCode";
    public final static String REFRESHER_CONTEST_DATA = FULL_SERVER_PATH + "/refresherContestData";
    public final static String REFRESHER_TEAMS_DATA = FULL_SERVER_PATH + "/refresherContestTeamsData";
    public final static String ALLIE_CONFIGURE_READY = FULL_SERVER_PATH + "/AllieConfigureIsReady";
    public final static String REFRESH_EXSIST_UBOAT = FULL_SERVER_PATH + "/refreshExistsUboat";
    public final static String UPDATE_EXIST_TEAM = FULL_SERVER_PATH + "/AllieUpdateExistTeamData";
    public final static String INITALIZE_ALLIES = FULL_SERVER_PATH + "/InitializeAlliesServlet";
    public final static String USERS_LIST = FULL_SERVER_PATH + "/userslist";
    public final static String LOGOUT = FULL_SERVER_PATH + "/chat/logout";
    public final static String SEND_CHAT_LINE = FULL_SERVER_PATH + "/pages/chatroom/sendChat";
    public final static String CHAT_LINES_LIST = FULL_SERVER_PATH + "/chat";

    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();
}
