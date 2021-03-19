package biz.ei6.interventions.desktop.framework.medias;

import biz.ei6.interventions.desktop.lib.data.MediasDataSource;
import biz.ei6.interventions.desktop.lib.domain.Media;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    public Media add(Media media) throws MediaPostException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Media> readAll() throws MediaGetException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Media readOne(String media_id) throws MediaGetException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Media media) throws MediaPutException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
