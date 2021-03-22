package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.lib.domain.Media;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 *
 * @author Eixa6
 */
public class MediaCellFactory implements Callback<ListView<Media>, ListCell<Media>> {

    @Override
    public ListCell<Media> call(ListView<Media> arg0) {
        return new MediaCell();
    }

    public class MediaCell extends ListCell<Media> {

        DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        @Override
        protected void updateItem(Media media, boolean empty) {
            super.updateItem(media, empty);

            if (empty) {
                setText(null);
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            } else {
                
                setText(media.getFileName() + " - " +Dateformatter.format(media.getDate()));
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

}
