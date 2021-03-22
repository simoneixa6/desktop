package biz.ei6.interventions.desktop.lib.interactors;

import biz.ei6.interventions.desktop.framework.medias.MediaGetException;
import biz.ei6.interventions.desktop.lib.data.MediasRepository;
import biz.ei6.interventions.desktop.lib.domain.Media;
import java.util.ArrayList;

/**
 *
 * @author Eixa6
 */
public class GetInterventionMedias {

    private final MediasRepository mediasRepository;

    public GetInterventionMedias(MediasRepository mediasRepository) {
        this.mediasRepository = mediasRepository;
    }

    public ArrayList<Media> invoke(String intervention_id) throws MediaGetException {
        return mediasRepository.getInterventionMedias(intervention_id);
    }
}
