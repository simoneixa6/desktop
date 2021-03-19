package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.framework.medias.MediaPostException;
import biz.ei6.interventions.desktop.lib.data.MediaFilesRepository;
import biz.ei6.interventions.desktop.lib.domain.Media;
import biz.ei6.interventions.desktop.lib.domain.MediaFile;

/**
 *
 * @author Eixa6
 */
public class AddMediaFile {

    private final MediaFilesRepository mediaFilesRepository;

    public AddMediaFile(MediaFilesRepository mediaFilesRepository) {
        this.mediaFilesRepository = mediaFilesRepository;
    }

    public Media invoke(MediaFile mediaFile) throws MediaPostException {
        return mediaFilesRepository.addMediaFile(mediaFile);
    }
}
