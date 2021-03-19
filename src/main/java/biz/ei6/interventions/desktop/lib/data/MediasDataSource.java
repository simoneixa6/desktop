package biz.ei6.interventions.desktop.lib.data;


import biz.ei6.interventions.desktop.framework.medias.MediaGetException;
import biz.ei6.interventions.desktop.framework.medias.MediaPostException;
import biz.ei6.interventions.desktop.framework.medias.MediaPutException;
import biz.ei6.interventions.desktop.lib.domain.Media;
import java.util.ArrayList;

/*
 * @author Eixa6
 */
public interface MediasDataSource {
    Media add(Media media) throws MediaPostException;
    ArrayList<Media> readAll() throws MediaGetException;
    Media readOne( String media_id ) throws MediaGetException;
    void remove( Media media ) throws MediaPutException; 
}
