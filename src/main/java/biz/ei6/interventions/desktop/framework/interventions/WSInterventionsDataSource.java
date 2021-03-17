package biz.ei6.interventions.desktop.framework.interventions;

import biz.ei6.interventions.desktop.framework.clients.ClientDTO;
import biz.ei6.interventions.desktop.framework.clients.SiteDTO;
import biz.ei6.interventions.desktop.lib.data.InterventionsDataSource;
import biz.ei6.interventions.desktop.lib.domain.Client;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import biz.ei6.interventions.desktop.lib.domain.Period;
import biz.ei6.interventions.desktop.lib.domain.Site;
import biz.ei6.interventions.desktop.lib.domain.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.util.ArrayList;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import static java.lang.Boolean.parseBoolean;
import java.util.List;
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

    private void setSiteDTO(SiteDTO siteDTO, Site site) {
        siteDTO.setAddress(site.getAddress());
        siteDTO.setZipCode(site.getZipCode());
        siteDTO.setCity(site.getCity());
    }

    private void setSite(Site site, SiteDTO siteDTO) {
        site.setAddress(siteDTO.getAddress());
        site.setZipCode(siteDTO.getZipCode());
        site.setCity(siteDTO.getCity());
    }

    private void setClientDTO(ClientDTO clientDTO, Client client) {
        clientDTO.setId(client.getId());
        clientDTO.setCivility(client.getCivility());
        clientDTO.setName(client.getName());
        clientDTO.setLastname(client.getLastname());
        clientDTO.setCompany(client.getCompany());
        clientDTO.setCompanyStatus(client.getCompanyStatus());
        clientDTO.setPhone(client.getPhone());
        clientDTO.setMail(client.getMail());
        clientDTO.setHow(client.getHow());
        clientDTO.setWhy(client.getWhy());
        clientDTO.setProblematic(String.valueOf(client.getProblematic()));
        clientDTO.setDeleted(String.valueOf(client.getDeleted()));

        ArrayList<SiteDTO> sitesDTO = new ArrayList<>();

        if (client.getAddresses() != null) {
            for (Site site : client.getAddresses()) {
                SiteDTO siteDTO = new SiteDTO();
                setSiteDTO(siteDTO, site);
                sitesDTO.add(siteDTO);
            }
            clientDTO.setAddresses(sitesDTO);
        }

        if (!"".equals(client.getFirstVisitDate())) {
            clientDTO.setFirstVisitDate(client.getFirstVisitDate());
        } else {
            // clientDTO.FirstVisitDate sera à null si aucune valeur choisi lors de la création
        }
    }

    private void setClient(Client client, ClientDTO clientDTO) {
        client.setId(clientDTO.getId());
        client.setCivility(clientDTO.getCivility());
        client.setName(clientDTO.getName());
        client.setLastname(clientDTO.getLastname());
        client.setCompany(clientDTO.getCompany());
        client.setCompanyStatus(clientDTO.getCompanyStatus());
        client.setPhone(clientDTO.getPhone());
        client.setMail(clientDTO.getMail());
        client.setHow(clientDTO.getHow());
        client.setWhy(clientDTO.getWhy());
        client.setProblematic(parseBoolean(clientDTO.getProblematic()));
        client.setDeleted(parseBoolean(clientDTO.getDeleted()));

        if (clientDTO.getAddresses() != null) {
            ArrayList<Site> sites = new ArrayList<>();

            for (biz.ei6.interventions.desktop.framework.clients.SiteDTO siteDTO : clientDTO.getAddresses()) {
                Site site = new Site();
                setSite(site, siteDTO);
                sites.add(site);
            }

            client.setAddresses(sites);
        }

        if ("0001-01-01T00:00:00Z".equals(clientDTO.getFirstVisitDate())) {
            // Le serveur renvoie la date 0001-01-01T00:00:00Z si aucune valeur de date n'a été rentré lors de la création du client
        } else {
            client.setFirstVisitDate(clientDTO.getFirstVisitDate());
        }
    }

    private void setPeriodDTO(PeriodDTO periodDTO, Period period) {

        if (period.getDate() != null) {
            periodDTO.setDate(period.getDateString());
        }

        if (period.getStart() != null) {
            periodDTO.setStart(period.getStartString());
        }

        if (period.getEnd() != null) {
            periodDTO.setEnd(period.getEndString());
        }
    }

    private void setPeriod(Period period, PeriodDTO periodDTO) {

        if ("0001-01-01T00:00:00Z".equals(periodDTO.getDate())) {
            // Le serveur renvoie la date 0001-01-01T00:00:00Z si aucune valeur de date n'a été rentré lors de la création d'une intervention
        } else {
            period.setDateString(periodDTO.getDate());
        }

        if ("0001-01-01T00:00:00Z".equals(periodDTO.getStart())) {
            // Le serveur renvoie la date 0001-01-01T00:00:00Z si aucune valeur de début n'a été rentré lors de la création d'une intervention
        } else {
            period.setStartString(periodDTO.getStart());
        }

        if ("0001-01-01T00:00:00Z".equals(periodDTO.getEnd())) {
            // Le serveur renvoie la date 0001-01-01T00:00:00Z si aucune valeur de début n'a été rentré lors de la création d'une intervention
        } else {
            period.setEndString(periodDTO.getEnd());
        }

    }

    
    
    private void setInterventionDTO(InterventionDTO interventionDTO, Intervention intervention) {
        interventionDTO.setId(intervention.getId());
        interventionDTO.setTitle(intervention.getTitle());
        interventionDTO.setUser_id(intervention.getUser_id());
        interventionDTO.setDescription(intervention.getDescription());
        interventionDTO.setBillNumber(intervention.getBillNumber());
        interventionDTO.setPaymentType(intervention.getPaymentType());
        interventionDTO.setMedias(intervention.getMedias());
        interventionDTO.setDeleted(String.valueOf(intervention.getDeleted()));

        if (intervention.getClient() != null) {
            ClientDTO clientDTO = new ClientDTO();
            setClientDTO(clientDTO, intervention.getClient());
            interventionDTO.setClient(clientDTO);
        }

        if (intervention.getAddress() != null) {
            SiteDTO siteDTO = new SiteDTO();
            setSiteDTO(siteDTO, intervention.getAddress());
            interventionDTO.setAddress(siteDTO);
        }

        if (intervention.getPeriods() != null) {
            List<PeriodDTO> periodsDTO = new ArrayList<>();

            for (Period period : intervention.getPeriods()) {
                PeriodDTO periodDTO = new PeriodDTO();
                setPeriodDTO(periodDTO, period);
                periodsDTO.add(periodDTO);
            }
            interventionDTO.setPeriods(periodsDTO);
        }

        if (intervention.getKm() != null && !"".equals(intervention.getKm())) {
            interventionDTO.setKm(Double.parseDouble(intervention.getKm()) + "");
        } else {
            // interventionDTO.Km sera à null si aucune valeur choisi lors de la création
        }

        if (intervention.getGoKm() != null && !"".equals(intervention.getGoKm()) ) {
            interventionDTO.setGoKm(Double.parseDouble(intervention.getGoKm()) + "");
        } else {
            // interventionDTO.Km sera à null si aucune valeur choisi lors de la création
        }

        if (intervention.getBackKm() != null && !"".equals(intervention.getBackKm())) {
            interventionDTO.setBackKm(Double.parseDouble(intervention.getBackKm()) + "");
        } else {
            // interventionDTO.Km sera à null si aucune valeur choisi lors de la création
        }

        if (intervention.getStatus() != null) {
            StatusDTO statusDTO = new StatusDTO();
            setStatusDTO(statusDTO, intervention.getStatus());
            interventionDTO.setStatus(statusDTO);
        }
        
        if (!"".equals(intervention.getBillDateString())) {
            interventionDTO.setBillDate(intervention.getBillDateString());
        } else {
            // interventionDTO.BillDate sera à null si aucune valeur choisi lors de la création
        }

        if (!"".equals(intervention.getPaymentDateString())) {
            interventionDTO.setPaymentDate(intervention.getPaymentDateString());
        } else {
            // interventionDTO.PaymentDate sera à null si aucune valeur choisi lors de la création
        }
    }

    private void setIntervention(Intervention intervention, InterventionDTO interventionDTO) {
        intervention.setId(interventionDTO.getId());
        intervention.setTitle(interventionDTO.getTitle());
        intervention.setUser_id(interventionDTO.getUser_id());
        intervention.setDescription(interventionDTO.getDescription());
        intervention.setBillNumber(interventionDTO.getBillNumber());
        intervention.setPaymentType(interventionDTO.getPaymentType());
        intervention.setMedias(interventionDTO.getMedias());
        intervention.setDeleted(parseBoolean(interventionDTO.getDeleted()));

        if (interventionDTO.getClient() != null) {
            Client client = new Client();
            setClient(client, interventionDTO.getClient());
            intervention.setClient(client);
        }

        if (interventionDTO.getStatus() != null) {
            Status status = new Status("","");
            setStatus(status, interventionDTO.getStatus());
            intervention.setStatus(status);
        }
        
        if (interventionDTO.getAddress() != null) {
            Site site = new Site();
            setSite(site, interventionDTO.getAddress());
            intervention.setAddress(site);
        }

        List<Period> periods = new ArrayList<>();

        if (interventionDTO.getPeriods() != null) {
            for (PeriodDTO periodDTO : interventionDTO.getPeriods()) {
                Period period = new Period();
                setPeriod(period, periodDTO);
                periods.add(period);
            }
        }
        
        intervention.setPeriods(periods);

        if ("0".equals(interventionDTO.getKm())) {
            // Le serveur renvoie 0 si aucune valeur n'a été rentré lors de la création d'une intervention
        } else {
            intervention.setKm(interventionDTO.getKm());
        }

        if ("0".equals(interventionDTO.getGoKm())) {
            // Le serveur renvoie 0 si aucune valeur n'a été rentré lors de la création d'une intervention
        } else {
            intervention.setGoKm(interventionDTO.getGoKm());
        }

        if ("0".equals(interventionDTO.getBackKm())) {
            // Le serveur renvoie 0 si aucune valeur n'a été rentré lors de la création d'une intervention
        } else {
            intervention.setBackKm(interventionDTO.getBackKm());
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

    private void setStatusDTO(StatusDTO statusDTO, Status status) {
        statusDTO.setId(status.getId());
        statusDTO.setName(status.getName());
    }
    
    private void setStatus(Status status, StatusDTO statusDTO) {
        status.setId(statusDTO.getId());
        status.setName(statusDTO.getName());
    }
}
