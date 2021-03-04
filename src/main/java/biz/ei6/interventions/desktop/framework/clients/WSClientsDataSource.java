package biz.ei6.interventions.desktop.framework.clients;

import biz.ei6.interventions.desktop.lib.data.ClientsDataSource;
import biz.ei6.interventions.desktop.lib.domain.Client;
import biz.ei6.interventions.desktop.lib.domain.Site;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import static java.lang.Boolean.parseBoolean;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

/*
 * @author Eixa6
 */
public class WSClientsDataSource implements ClientsDataSource {

    HttpClient httpClient;
    ResourceBundle resources;

    public WSClientsDataSource(ResourceBundle resources, HttpClient httpClient) {
        this.resources = resources;
        this.httpClient = httpClient;
    }

    @Override
    public void add(Client client) throws ClientPostException {

        String serverResp = null;

        try {
            String json = jsonCreation(client);

            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/clientswr"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            serverResp = resp.toString();

        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            throw new ClientPostException(resources.getString("exception.reponseServeur") + serverResp + resources.getString("exception.exception") + e, e);
        }
    }

    @Override
    public ArrayList<Client> readAll() throws ClientGetException {

        String serverResp = null;

        ArrayList<Client> clients = new ArrayList<>();

        try {
            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/clients"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            var res = resp.body();

            serverResp = resp.toString();

            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            var clientsDTO = om.readValue(res, ClientDTO[].class);

            // Ajout des interventions reçues dans l'ArrayList<Intervention>
            for (var clientDTO : clientsDTO) {
                // Si l'intervention n'est pas supprimé "deleted==true", on l'ajoute 
                if (parseBoolean(clientDTO.getDeleted()) == false) {
                    Client client = new Client();

                    setClient(client, clientDTO);

                    clients.add(client);
                }
            }
        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            throw new ClientGetException(resources.getString("exception.reponseServeur") + serverResp + resources.getString("exception.exception") + e, e);
        }
        return clients;
    }

    @Override
    public void update(Client client) throws ClientPutException {

        String serverResp = null;

        try {

            String json = jsonCreation(client);

            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/clientswr/" + client.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            serverResp = resp.toString();

        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            throw new ClientPutException(resources.getString("exception.reponseServeur") + serverResp + resources.getString("exception.exception") + e, e);
        }
    }

    @Override
    public void remove(Client client) throws ClientPutException {

        String serverResp = null;

        try {

            client.setDeleted(true);

            String json = jsonCreation(client);

            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("https://simon.biz/clientswr/" + client.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            serverResp = resp.toString();

        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            throw new ClientPutException(resources.getString("exception.reponseServeur") + serverResp + resources.getString("exception.exception") + e, e);
        }
    }

    private String jsonCreation(Client client) throws JsonProcessingException {
        ClientDTO clientDTO = new ClientDTO();
        setClientDTO(clientDTO, client);
        ObjectMapper om = new ObjectMapper();
        var json = om.writeValueAsString(clientDTO);
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

            for (SiteDTO siteDTO : clientDTO.getAddresses()) {
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
}
