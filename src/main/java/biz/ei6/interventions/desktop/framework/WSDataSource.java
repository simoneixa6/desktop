/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.framework;

import biz.ei6.interventions.desktop.lib.data.InterventionsDataSource;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import biz.ei6.interventions.desktop.lib.domain.InterventionFake;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.ExecutionException;
import javafx.scene.control.Alert;

/*
 * @author Eixa6
 */
public class WSDataSource implements InterventionsDataSource {

    static HttpClient httpClient;

    static {
        // Création du client HTTP
        httpClient = HttpClient.newHttpClient();
    }

    @Override
    public void add(Intervention intervention) {

        try {

            ObjectMapper om = new ObjectMapper();

            var data = om.writeValueAsString(intervention);

            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/interventionswr"))
                    .header("Content-Type", "application/json")
                    //.version(HttpClient.Version.HTTP_1_1)
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            String resultat = resp.toString();

        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            new Alert(Alert.AlertType.ERROR, "Erreur lors du post de l'intervention au webservice : " + e.toString()).show();
        }
    }

    @Override
    public ArrayList<Intervention> readAll() {

        ArrayList<Intervention> interventions = new ArrayList<Intervention>();

        try {
            // Création du client HTTP
            //var httpClient = HttpClient.newHttpClient();

            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/interventions"))
                    .header("Accept", "application/json")
                    //.version(HttpClient.Version.HTTP_1_1)
                    .GET()
                    .build();

            var response = httpClient.sendAsync(request, new JsonBodyHandler<InterventionFake[]>(InterventionFake[].class));

            // This blocks until the request is complete
            var resp = response.get();
            var head = resp.headers();

            var res = resp.body().get();

            System.out.println(res.toString());
//            for (var c : res) {
//                interventions.add(c);
//            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erreur lors du get des interventions au webservice : " + e.toString()).show();
        }

        return interventions;
    }

    @Override
    public void update(Intervention intervention) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
