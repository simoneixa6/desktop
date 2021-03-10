package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.lib.domain.Site;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 *
 * @author Eixa6
 */
public class SiteCellFactory implements Callback<ListView<Site>, ListCell<Site>> {

    @Override
    public ListCell<Site> call(ListView<Site> arg0) {
        return new SiteCell();
    }

    public class SiteCell extends ListCell<Site> {

        @Override
        protected void updateItem(Site site, boolean empty) {
            super.updateItem(site, empty);

            // Ajout du style pour une cell
            Background mainGreyBackground = new Background(new BackgroundFill(Color.web("F2F2F2"), null, null));
            Background hoverGreyBackground = new Background(new BackgroundFill(Color.web("EAEAEA"), null, null));
            setBackground(mainGreyBackground);
            setTextFill(Color.BLACK);
            setOnMouseEntered(event -> {
                setBackground(hoverGreyBackground);
            });
            setOnMouseExited(event -> {
                setBackground(mainGreyBackground);
            });

            if (site == null || empty) {
                setGraphic(null);
            } else {
                setText(site.getAddress() + ", " + site.getZipCode() + " " + site.getCity());
            }
        }
    }
}
