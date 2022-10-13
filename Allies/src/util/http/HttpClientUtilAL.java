package util.http;

import okhttp3.*;

import java.util.function.Consumer;

import static util.ConstantsAL.UPLOAD_FILE;

public class HttpClientUtilAL {

    private final static SimpleCookieManager simpleCookieManager = new SimpleCookieManager();
    private final static OkHttpClient HTTP_CLIENT =
            new OkHttpClient.Builder()
                    .cookieJar(simpleCookieManager)
                    .followRedirects(false)
                    .build();

    public static void setCookieManagerLoggingFacility(Consumer<String> logConsumer) {
        simpleCookieManager.setLogData(logConsumer);
    }

    public static void removeCookiesOf(String domain) {
        simpleCookieManager.removeCookiesOf(domain);
    }

    public static void runAsync(String finalUrl, Callback callback) {
        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = HttpClientUtilAL.HTTP_CLIENT.newCall(request);

        call.enqueue(callback);
    }

    public static void runAsyncPostRequest(RequestBody body, String URL, Callback callback){
        Request request = new Request.Builder()
                .url(UPLOAD_FILE)
                .post(body)
                .build();

        Call call = HttpClientUtilAL.HTTP_CLIENT.newCall(request);

        call.enqueue(callback);
    }

    public static void shutdown() {
        System.out.println("Shutting down HTTP CLIENT");
        HTTP_CLIENT.dispatcher().executorService().shutdown();
        HTTP_CLIENT.connectionPool().evictAll();
    }
}
