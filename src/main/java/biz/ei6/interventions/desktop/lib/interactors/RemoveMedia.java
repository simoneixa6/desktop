package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.framework.medias.MediaPutException;
import biz.ei6.interventions.desktop.lib.data.MediasRepository;
import biz.ei6.interventions.desktop.lib.domain.Media;

/**
 *
 * @author Eixa6
 */
public class RemoveMedia {

    private final MediasRepository mediasRepository;

    public RemoveMedia(MediasRepository clientsRepository) {
        this.mediasRepository = clientsRepository;
    }

    public void invoke(Media media) throws MediaPutException {
        mediasRepository.removeMedia(media);
    }
}
