package biz.ei6.interventions.desktop.framework.medias;

import biz.ei6.interventions.desktop.lib.data.MediaFilesDataSource;
import biz.ei6.interventions.desktop.lib.domain.Media;
import biz.ei6.interventions.desktop.lib.domain.MediaFile;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;

/*
 * @author Eixa6
 */
public class WSMediaFilesDataSource implements MediaFilesDataSource {

    HttpClient httpClient;
    ResourceBundle resources;

    public WSMediaFilesDataSource(ResourceBundle resources, HttpClient httpClient) {
        this.resources = resources;
        this.httpClient = httpClient;
    }

    @Override
    public Media add(MediaFile mediaFile) throws MediaPostException {

        ObjectMapper om = new ObjectMapper();
        Media addedMedia = null;
        
        try {
            var json = om.writeValueAsString(mediaFile);

            // Création de la requête
            var request = HttpRequest.newBuilder(
                    URI.create("http://localhost:60396/medias"))
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
            throw new MediaPostException("Erreur lors de l'upload du fichier", e);
        }

        return addedMedia;
    }

    @Override
    public MediaFile readOne(String mediaFile_url) throws MediaGetException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(String mediaFile_url) throws MediaPutException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setMedia(Media addedMedia, MediaDTO mediaDTO) {
        addedMedia.setId(mediaDTO.getId());
        addedMedia.setDate(mediaDTO.getDate());
        addedMedia.setFileName(mediaDTO.getFileName());
        addedMedia.setInterventionId(mediaDTO.getInterventionId());
    }
}
