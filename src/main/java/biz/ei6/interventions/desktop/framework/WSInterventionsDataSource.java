package biz.ei6.interventions.desktop.framework;

import biz.ei6.interventions.desktop.lib.data.InterventionsDataSource;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import static java.lang.Boolean.parseBoolean;
import java.util.concurrent.ExecutionException;
import javafx.scene.control.Alert;

/*
 * @author Eixa6
 */
public class WSInterventionsDataSource implements InterventionsDataSource {

    static HttpClient httpClient;

    static {
        // Création du client HTTP
        httpClient = HttpClient.newHttpClient();
    }

    @Override
    public void add(Intervention intervention) {
        try {

            InterventionDTO interventionDTO = new InterventionDTO();

            setInterventionDTO(interventionDTO, intervention);

            ObjectMapper om = new ObjectMapper();

            var data = om.writeValueAsString(interventionDTO);

            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/interventionswr"))
                    .header("Content-Type", "application/json")
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
            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/interventions"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            var response = httpClient.sendAsync(request, new JsonBodyHandler<InterventionDTO[]>(InterventionDTO[].class));

            var resp = response.get();

            var interventionsDTO = resp.body().get();

            for (var interventionDTO : interventionsDTO) {

                if (parseBoolean(interventionDTO.getDeleted()) == false) {
                    Intervention intervention = new Intervention();

                    setIntervention(intervention, interventionDTO);

                    interventions.add(intervention);
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erreur lors du get des interventions au webservice : " + e.toString()).show();
        }

        return interventions;
    }

    @Override
    public void update(Intervention intervention) {
        try {

            InterventionDTO interventionDTO = new InterventionDTO();

            setInterventionDTO(interventionDTO, intervention);

            ObjectMapper om = new ObjectMapper();

            var data = om.writeValueAsString(interventionDTO);

            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/interventionswr/" + interventionDTO.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            String resultat = resp.toString();

        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            new Alert(Alert.AlertType.ERROR, "Erreur lors de la mise à jour de l'intervention au webservice : " + e.toString()).show();
        }

    }

    @Override
    public void remove(Intervention intervention) {

        try {

            InterventionDTO interventionDTO = new InterventionDTO();

            setInterventionDTO(interventionDTO, intervention);

            interventionDTO.setDeleted("true");

            ObjectMapper om = new ObjectMapper();

            var data = om.writeValueAsString(interventionDTO);

            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/interventionswr/" + interventionDTO.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            String resultat = resp.toString();

        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            new Alert(Alert.AlertType.ERROR, "Erreur lors de la suppression de l'intervention sur le webservice : " + e.toString()).show();
        }
    }

    private void setInterventionDTO(InterventionDTO interventionDTO, Intervention intervention) {
        interventionDTO.setId(intervention.getId());
        interventionDTO.setTitle(intervention.getTitle());
        interventionDTO.setClient_id(intervention.getClient_id());
        interventionDTO.setDescription(intervention.getDescription());
        // interventionDTO.setPeriods(new ArrayList<PeriodDTO>());
        interventionDTO.setAddress(intervention.getAddress());
        interventionDTO.setKm(intervention.getKm());
        interventionDTO.setBillNumber(intervention.getBillNumber());
        interventionDTO.setBillDate(intervention.getBillDate());
        interventionDTO.setPaymentType(intervention.getPaymentType());
        interventionDTO.setPaymentDate(intervention.getPaymentDate());
        interventionDTO.setStatus(intervention.getStatus());
        // interventionDTO.setMedias(new ArrayList<String>());
        interventionDTO.setDeleted(String.valueOf(intervention.getDeleted()));
    }

    private void setIntervention(Intervention intervention, InterventionDTO interventionDTO) {
        intervention.setId(interventionDTO.getId());
        intervention.setTitle(interventionDTO.getTitle());
        intervention.setClient_id(interventionDTO.getClient_id());
        intervention.setDescription(interventionDTO.getDescription());
        // intervention.setPeriods(new ArrayList<PeriodDTO>());
        intervention.setAddress(interventionDTO.getAddress());
        intervention.setKm(interventionDTO.getKm());
        intervention.setBillNumber(interventionDTO.getBillNumber());
        intervention.setBillDate(interventionDTO.getBillDate());
        intervention.setPaymentType(interventionDTO.getPaymentType());
        intervention.setPaymentDate(interventionDTO.getPaymentDate());
        intervention.setStatus(interventionDTO.getStatus());
        // intervention.setMedias(new ArrayList<String>());
        intervention.setDeleted(parseBoolean(interventionDTO.getDeleted()));
    }
}
