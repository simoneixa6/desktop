package biz.ei6.interventions.desktop.lib.data;

import biz.ei6.interventions.desktop.framework.medias.MediaGetException;
import biz.ei6.interventions.desktop.framework.medias.MediaPostException;
import biz.ei6.interventions.desktop.framework.medias.MediaPutException;
import biz.ei6.interventions.desktop.lib.domain.Media;
import java.util.ArrayList;

/*
 * @author Eixa6
 */
public class MediasRepository {

    private final MediasDataSource mediasDataSource;

    public MediasRepository(MediasDataSource mediasDataSource) {
        this.mediasDataSource = mediasDataSource;
    }

    public Media addMedia(Media media) throws MediaPostException {
       return mediasDataSource.add(media);
    }

    public ArrayList<Media> getMedias() throws MediaGetException {
        return mediasDataSource.readAll();
    }

    public Media getMedia(String media_id) throws MediaGetException {
        return mediasDataSource.readOne(media_id);
    }

    public void removeMedia(Media media) throws MediaPutException {
        mediasDataSource.remove(media);
    }

}
