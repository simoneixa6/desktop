package biz.ei6.interventions.desktop.lib.data;

import biz.ei6.interventions.desktop.framework.medias.MediaGetException;
import biz.ei6.interventions.desktop.framework.medias.MediaPostException;
import biz.ei6.interventions.desktop.lib.domain.Media;
import biz.ei6.interventions.desktop.lib.domain.MediaFile;

/*
 * @author Eixa6
 */
public class MediaFilesRepository {

    private final MediaFilesDataSource mediaFilesDataSource;

    public MediaFilesRepository(MediaFilesDataSource mediaFilesDataSource) {
        this.mediaFilesDataSource = mediaFilesDataSource;
    }

    public Media addMediaFile(MediaFile mediaFile) throws MediaPostException {
       return mediaFilesDataSource.add(mediaFile);
    }

    public MediaFile getMediaFile(String media_id) throws MediaGetException {
        return mediaFilesDataSource.readOne(media_id);
    }
}
