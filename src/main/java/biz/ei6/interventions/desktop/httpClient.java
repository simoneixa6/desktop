package biz.ei6.interventions.desktop;

import java.net.http.HttpClient;

/**
 *
 * @author Eixa6
 */
public class httpClient {

    private static HttpClient instance;

    public HttpClient get() {
        if (instance == null) {
            instance = HttpClient.newHttpClient();
        }
        return instance;
    }
}
