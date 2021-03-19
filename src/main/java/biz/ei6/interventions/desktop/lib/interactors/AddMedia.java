package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.framework.medias.MediaPostException;
import biz.ei6.interventions.desktop.lib.data.MediasRepository;
import biz.ei6.interventions.desktop.lib.domain.Media;

/**
 * @author Eixa6
 */
public class AddMedia {
    private final MediasRepository mediasRepository;
    
    public AddMedia(MediasRepository mediasRepository) { this.mediasRepository=mediasRepository;}
    
    public Media invoke(Media media) throws MediaPostException {
        return mediasRepository.addMedia(media);
    }
}
