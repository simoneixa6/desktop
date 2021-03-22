package biz.ei6.interventions.desktop.lib.data;


import biz.ei6.interventions.desktop.framework.medias.MediaGetException;
import biz.ei6.interventions.desktop.framework.medias.MediaPostException;
import biz.ei6.interventions.desktop.framework.medias.MediaPutException;
import biz.ei6.interventions.desktop.lib.domain.Media;
import biz.ei6.interventions.desktop.lib.domain.MediaFile;

/*
 * @author Eixa6
 */
public interface MediaFilesDataSource {
    Media add(MediaFile mediaFile) throws MediaPostException;  
    MediaFile readOne( String media_id ) throws MediaGetException;
}
