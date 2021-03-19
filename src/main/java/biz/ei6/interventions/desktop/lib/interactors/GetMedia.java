package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.framework.medias.MediaGetException;
import biz.ei6.interventions.desktop.lib.data.MediasRepository;
import biz.ei6.interventions.desktop.lib.domain.Media;

/**
 *
 * @author Eixa6
 */
public class GetMedia {
    
    private final MediasRepository mediasRepository;

    public GetMedia(MediasRepository mediasRepository) {
        this.mediasRepository = mediasRepository;
    }

    public Media invoke(String media_id) throws MediaGetException {
        return mediasRepository.getMedia(media_id);
    }
    
}
