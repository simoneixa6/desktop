package biz.ei6.interventions.desktop.framework.medias;

import biz.ei6.interventions.desktop.lib.data.MediasDataSource;
import biz.ei6.interventions.desktop.lib.domain.Media;
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
public class WSMediasDataSource implements MediasDataSource {

    HttpClient httpClient;
    ResourceBundle resources;

    public WSMediasDataSource(ResourceBundle resources, HttpClient httpClient) {
        this.resources = resources;
        this.httpClient = httpClient;
    }

    @Override
    public ArrayList<Media> readAll() throws MediaGetException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Media> readAllForIntervention(String intervention_id) throws MediaGetException {

        String serverResp = null;

        ArrayList<Media> medias = new ArrayList<>();

        try {
            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("http://localhost:60396/medias/intervention/" + intervention_id))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            var res = resp.body();

            serverResp = resp.toString();

            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            var mediasDTO = om.readValue(res, MediaDTO[].class);

            // Ajout des interventions reçues dans l'ArrayList<Intervention>
            for (var mediaDTO : mediasDTO) {
                // Si le media n'est pas supprimé, on l'ajoute 
                if (parseBoolean(mediaDTO.getDeleted()) == false) {
                    Media media = new Media();

                    setMedia(media, mediaDTO);

                    medias.add(media);
                }
            }
        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            StringBuilder exception = new StringBuilder();
            exceptionBuilder(serverResp, exception, e);
            throw new MediaGetException(exception.toString(), e);
        }
        return medias;
    }

    @Override
    public Media readOne(String media_id) throws MediaGetException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Media media) throws MediaPutException {
        
        String serverResp = null;

        try {

            media.setDeleted(true);

            String json = jsonCreation(media);

            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("http://localhost:60396/medias/" + media.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            //var resp = response.get();

            //serverResp = resp.toString();

        } catch (Exception e) {
            StringBuilder exception = new StringBuilder();
            exceptionBuilder(serverResp, exception, e);
            throw new MediaPutException(exception.toString(), e);
        }
    }

    private String jsonCreation(Media media) throws JsonProcessingException {
        MediaDTO mediaDTO = new MediaDTO();
        setMediaDTO(mediaDTO, media);
        ObjectMapper om = new ObjectMapper();
        var json = om.writeValueAsString(mediaDTO);
        return json;
    }
    
    
    private void exceptionBuilder(String serverResp, StringBuilder exception, Exception e) {
        if (serverResp != null) {
            exception.append(resources.getString("exception.reponseServeur")).append(serverResp);
        }
        exception.append(resources.getString("exception.exception"));
        exception.append(e);
    }

    private void setMedia(Media addedMedia, MediaDTO mediaDTO) {
        addedMedia.setId(mediaDTO.getId());
        addedMedia.setDate(mediaDTO.getDate());
        addedMedia.setFileName(mediaDTO.getFileName());
        addedMedia.setInterventionId(mediaDTO.getIntervention_id());
        addedMedia.setDeleted(Boolean.valueOf(mediaDTO.getDeleted()));
    }

    private void setMediaDTO(MediaDTO mediaDTO, Media media) {
        mediaDTO.setId(media.getId());
        mediaDTO.setDate(media.getDateString());
        mediaDTO.setFileName(media.getFileName());
        mediaDTO.setIntervention_id(media.getInterventionId());
        mediaDTO.setDeleted(String.valueOf(media.getDeleted()));
    }
}
