package biz.ei6.interventions.desktop.interventions;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/*
 * @author Eixa6
 */
public class SortBoxCellFactory implements Callback<ListView<String>, ListCell<String>>{
    @Override
    public ListCell<String> call(ListView<String> param) {
        return new SortBoxCell();
    }
}
