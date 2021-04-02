package biz.ei6.interventions.desktop.framework.medias;

import biz.ei6.interventions.desktop.httpClient;
import biz.ei6.interventions.desktop.lib.data.MediaFilesDataSource;
import biz.ei6.interventions.desktop.lib.domain.Media;
import biz.ei6.interventions.desktop.lib.domain.MediaFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

/*
 * @author Eixa6
 */
public class WSMediaFilesDataSource implements MediaFilesDataSource {

    HttpClient httpClient = new httpClient().get();
    ResourceBundle resources;

    String url = "https://simon.biz/medias";
    
    public WSMediaFilesDataSource(ResourceBundle resources) {
        this.resources = resources;
    }

    @Override
    public Media add(MediaFile mediaFile) throws MediaPostException {

        String serverResp = null;
        ObjectMapper om = new ObjectMapper();
        Media addedMedia = new Media();

        try {
            var json = om.writeValueAsString(mediaFile);
      
            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            var res = resp.body();

            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            var mediaDTO = om.readValue(res, MediaDTO.class);

            setMedia(addedMedia, mediaDTO);

        } catch (Exception e) {
            StringBuilder exception = new StringBuilder();
            exceptionBuilder(serverResp, exception, e);
            throw new MediaPostException(exception.toString(), e);
        }

        return addedMedia;
    }

    @Override
    public MediaFile readOne(String media_id) throws MediaGetException {

        String serverResp = null;
        MediaFile mediaFile = null;

        try {
            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create(url + media_id + "/file"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            var resp = response.get();

            var res = resp.body();

            serverResp = resp.toString();

            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            mediaFile = om.readValue(res, MediaFile.class);

        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            StringBuilder exception = new StringBuilder();
            exceptionBuilder(serverResp, exception, e);
            throw new MediaGetException(exception.toString(), e);
        }
        return mediaFile;
    }

    private void setMedia(Media addedMedia, MediaDTO mediaDTO) {
        addedMedia.setId(mediaDTO.getId());
        addedMedia.setDate(mediaDTO.getDate());
        addedMedia.setFileName(mediaDTO.getFileName());
        addedMedia.setMimeType(mediaDTO.getMimeType());
        addedMedia.setInterventionId(mediaDTO.getIntervention_id());
    }

    private void exceptionBuilder(String serverResp, StringBuilder exception, Exception e) {
        if (serverResp != null) {
            exception.append(resources.getString("exception.reponseServeur")).append(serverResp);
        }
        exception.append(resources.getString("exception.exception"));
        exception.append(e);
    }
}
