package biz.ei6.interventions.desktop.framework.interventions;

import biz.ei6.interventions.desktop.lib.data.InterventionsDataSource;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.util.ArrayList;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import static java.lang.Boolean.parseBoolean;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;


/*
 * @author Eixa6
 */
public class WSInterventionsDataSource implements InterventionsDataSource {

    HttpClient httpClient;
    ResourceBundle resources;

    public WSInterventionsDataSource(ResourceBundle resources, HttpClient httpClient) {
        this.resources = resources;
        this.httpClient = httpClient;
    }

    @Override
    public void add(Intervention intervention) throws InterventionPostException {

        String serverResp = null;

        try {

            String json = jsonCreation(intervention);

            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/interventionswr"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            serverResp = resp.toString();

        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            throw new InterventionPostException(resources.getString("exception.reponseServeur") + serverResp + resources.getString("exception.exception") + e, e);
        }
    }

    @Override
    public ArrayList<Intervention> readAll() throws InterventionGetException {

        String serverResp = null;

        ArrayList<Intervention> interventions = new ArrayList<>();

        try {
            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/interventions"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            var res = resp.body();

            serverResp = resp.toString();

            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            var interventionsDTO = om.readValue(res, InterventionDTO[].class);

            // Ajout des interventions reçues dans l'ArrayList<Intervention>
            for (var interventionDTO : interventionsDTO) {
                // Si l'intervention n'est pas supprimé "deleted==true", on l'ajoute 
                if (parseBoolean(interventionDTO.getDeleted()) == false) {
                    Intervention intervention = new Intervention();

                    setIntervention(intervention, interventionDTO);

                    interventions.add(intervention);
                }
            }
        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            throw new InterventionGetException(resources.getString("exception.reponseServeur") + serverResp + resources.getString("exception.exception") + e, e);
        }
        return interventions;
    }

    @Override
    public void update(Intervention intervention) throws InterventionPutException {

        String serverResp = null;

        try {

            String json = jsonCreation(intervention);

            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/interventionswr/" + intervention.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            serverResp = resp.toString();

        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            throw new InterventionPutException(resources.getString("exception.reponseServeur") + serverResp + resources.getString("exception.exception") + e, e);
        }
    }

    @Override
    public void remove(Intervention intervention) throws InterventionPutException {

        String serverResp = null;

        try {

            intervention.setDeleted(true);

            String json = jsonCreation(intervention);

            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/interventionswr/" + intervention.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            serverResp = resp.toString();

        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            throw new InterventionPutException(resources.getString("exception.reponseServeur") + serverResp + resources.getString("exception.exception") + e, e);
        }
    }

    private String jsonCreation(Intervention intervention) throws JsonProcessingException {
        InterventionDTO interventionDTO = new InterventionDTO();
        setInterventionDTO(interventionDTO, intervention);
        ObjectMapper om = new ObjectMapper();
        var json = om.writeValueAsString(interventionDTO);
        return json;
    }

    private void setInterventionDTO(InterventionDTO interventionDTO, Intervention intervention) {
        interventionDTO.setId(intervention.getId());
        interventionDTO.setTitle(intervention.getTitle());
        interventionDTO.setClient_id(intervention.getClient_id());
        interventionDTO.setUser_id(intervention.getUser_id());
        interventionDTO.setDescription(intervention.getDescription());
        // interventionDTO.setPeriods(new ArrayList<PeriodDTO>());
        interventionDTO.setAddress(intervention.getAddress());
        interventionDTO.setBillNumber(intervention.getBillNumber());
        interventionDTO.setPaymentType(intervention.getPaymentType());
        interventionDTO.setStatus(intervention.getStatus());
        interventionDTO.setMedias(intervention.getMedias());
        interventionDTO.setDeleted(String.valueOf(intervention.getDeleted()));

        if (intervention.getKm() != null) {
            interventionDTO.setKm(Double.parseDouble(intervention.getKm()) + "");
        } else {
            // interventionDTO.Km sera à null si aucune valeur choisi lors de la création
        }

        if (!"".equals(intervention.getBillDate())) {
            interventionDTO.setBillDate(intervention.getBillDate());
        } else {
            // interventionDTO.BillDate sera à null si aucune valeur choisi lors de la création
        }

        if (!"".equals(intervention.getPaymentDate())) {
            interventionDTO.setPaymentDate(intervention.getPaymentDate());
        } else {
            // interventionDTO.PaymentDate sera à null si aucune valeur choisi lors de la création
        }
    }

    private void setIntervention(Intervention intervention, InterventionDTO interventionDTO) {
        intervention.setId(interventionDTO.getId());
        intervention.setTitle(interventionDTO.getTitle());
        intervention.setClient_id(interventionDTO.getClient_id());
        intervention.setUser_id(interventionDTO.getUser_id());
        intervention.setDescription(interventionDTO.getDescription());
        // intervention.setPeriods(new ArrayList<PeriodDTO>());
        intervention.setAddress(interventionDTO.getAddress());
        intervention.setBillNumber(interventionDTO.getBillNumber());
        intervention.setPaymentType(interventionDTO.getPaymentType());
        intervention.setStatus(interventionDTO.getStatus());
        intervention.setMedias(interventionDTO.getMedias());
        intervention.setDeleted(parseBoolean(interventionDTO.getDeleted()));

        if ("0".equals(interventionDTO.getKm())) {
            // Le serveur renvoie 0 si aucune valeur n'a été rentré lors de la création d'une intervention
        } else {
            intervention.setKm(interventionDTO.getKm());
        }

        if ("0001-01-01T00:00:00Z".equals(interventionDTO.getBillDate())) {
            // Le serveur renvoie la date 0001-01-01T00:00:00Z si aucune valeur de date n'a été rentré lors de la création d'une intervention
        } else {
            intervention.setBillDate(interventionDTO.getBillDate());
        }

        if ("0001-01-01T00:00:00Z".equals(interventionDTO.getPaymentDate())) {
            // Le serveur renvoie la date 0001-01-01T00:00:00Z si aucune valeur de date n'a été rentré lors de la création d'une intervention
        } else {
            intervention.setPaymentDate(interventionDTO.getPaymentDate());
        }
    }
}
