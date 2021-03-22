package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.framework.medias.MediaGetException;
import biz.ei6.interventions.desktop.lib.data.MediaFilesRepository;
import biz.ei6.interventions.desktop.lib.domain.MediaFile;

/**
 *
 * @author Eixa6
 */
public class GetMediaFile {
    
    private final MediaFilesRepository mediaFilesRepository;

    public GetMediaFile(MediaFilesRepository mediaFilesRepository) {
        this.mediaFilesRepository = mediaFilesRepository;
    }

    public MediaFile invoke(String media_id) throws MediaGetException {
        return mediaFilesRepository.getMediaFile(media_id);
    }
}
