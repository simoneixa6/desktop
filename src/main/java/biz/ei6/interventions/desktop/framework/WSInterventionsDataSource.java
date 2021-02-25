package biz.ei6.interventions.desktop.framework;

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

        } catch (Exception e) {
            //           throw new InterventionPostException("Erreur lors du POST",e);
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

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            var res = resp.body();

            ObjectMapper om = new ObjectMapper();

            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            var interventionsDTO = om.readValue(res, InterventionDTO[].class);

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
        interventionDTO.setBillNumber(intervention.getBillNumber());
        interventionDTO.setPaymentType(intervention.getPaymentType());
        interventionDTO.setStatus(intervention.getStatus());
        // interventionDTO.setMedias(new ArrayList<String>());
        interventionDTO.setDeleted(String.valueOf(intervention.getDeleted()));

        if (intervention.getKm() != null) {
            interventionDTO.setKm(Double.parseDouble(intervention.getKm()) + "");
        } else {
            // Pour que Fabien comprenne bien, interventionDTO.Km sera à null
        }

        if (!"".equals(intervention.getBillDate())) {
            interventionDTO.setBillDate(intervention.getBillDate());
        } else {
            // Pour que Fabien comprenne bien, interventionDTO.Km sera à null
        }

        if (!"".equals(intervention.getPaymentDate())) {
            interventionDTO.setPaymentDate(intervention.getPaymentDate());
        } else {
            // Pour que Fabien comprenne bien, interventionDTO.Km sera à null
        }
    }

    private void setIntervention(Intervention intervention, InterventionDTO interventionDTO) {
        intervention.setId(interventionDTO.getId());
        intervention.setTitle(interventionDTO.getTitle());
        intervention.setClient_id(interventionDTO.getClient_id());
        intervention.setDescription(interventionDTO.getDescription());
        // intervention.setPeriods(new ArrayList<PeriodDTO>());
        intervention.setAddress(interventionDTO.getAddress());
        intervention.setBillNumber(interventionDTO.getBillNumber());
        intervention.setPaymentType(interventionDTO.getPaymentType());
        intervention.setStatus(interventionDTO.getStatus());
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

        // intervention.setMedias(new ArrayList<String>());
    }
}
