package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.App;
import biz.ei6.interventions.desktop.lib.domain.Intervention;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 *
 * @author Eixa6
 */
public class InterventionCellFactory implements Callback<ListView<Intervention>, ListCell<Intervention>> {

    //Temporaire
    App.Interactors interactors;
    
    InterventionCellFactory(App.Interactors interactors) {
        this.interactors = interactors;
    }
        @Override
        public ListCell<Intervention> call(ListView<Intervention> param) {
            return new InterventionCell(interactors);
        }
}
