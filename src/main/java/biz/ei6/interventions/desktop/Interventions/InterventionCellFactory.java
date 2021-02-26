package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.lib.domain.Intervention;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 *
 * @author Eixa6
 */
public class InterventionCellFactory implements Callback<ListView<Intervention>, ListCell<Intervention>> {
        @Override
        public ListCell<Intervention> call(ListView<Intervention> param) {
            return new InterventionCell();
        }
}
