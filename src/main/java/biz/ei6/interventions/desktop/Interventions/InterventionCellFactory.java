package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        public class InterventionCell  extends ListCell<Intervention> {

        @FXML
        Label lblIntervention;

        @FXML
        ImageView status;

        public InterventionCell() {
            loadFXML();
        }

         private void loadFXML() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("interventionCell.fxml"));
                loader.setController(this);
                loader.setRoot(this);
                loader.load();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        @Override
        protected void updateItem(Intervention intervention, boolean empty) {
            super.updateItem(intervention, empty);

            if(empty) {
                setText(null);
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
            else {

                DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                
                StringBuilder interventionString = new StringBuilder();
                
                interventionString.append(Dateformatter.format(intervention.getPeriods().get(0).getDate()) + "  ");

                // Si il y a un titre d'intervention
                if ( intervention.getTitle() != null ){
                    interventionString.append(intervention.getTitle() + " - ");
                }
                // Si il y a un prénom de client
                if ( intervention.getClient().getName() != null ){
                    interventionString.append(intervention.getClient().getName() + " ");
                }
                // Si il y a un nom de client
                if ( intervention.getClient().getLastname() != null ){
                    interventionString.append(intervention.getClient().getLastname() + " ");
                } 
                // Si il y a un nom d'entreprise pour ce client
                if (intervention.getClient().getCompany() != null) {
                    if (intervention.getClient().getName() != null || intervention.getClient().getLastname() != null) {
                        interventionString.append(" (" + intervention.getClient().getCompany()+")");
                    } else {
                        interventionString.append(intervention.getClient().getCompany());
                    }
                }
                

                lblIntervention.setText(interventionString.toString());

                InputStream redImageStream = getClass().getResourceAsStream("red.png");
                InputStream yellowImageStream = getClass().getResourceAsStream("yellow.png");
                InputStream orangeImageStream = getClass().getResourceAsStream("orange.png");
                InputStream greenImageStream = getClass().getResourceAsStream("green.png");

                Image redDot = new Image(redImageStream);
                Image yellowDot = new Image(yellowImageStream);
                Image orangeDot = new Image(orangeImageStream);
                Image greenDot = new Image(greenImageStream);

                    switch(Integer.parseInt(intervention.getStatus().getId())){
                        case 1:
                            status.setImage(redDot);  
                            break;
                        case 2:
                            status.setImage(yellowDot);
                            break;
                        case 3:
                            status.setImage(orangeDot);
                            break;
                        case 4:
                            status.setImage(greenDot);
                            break;
                        default:
                            throw new RuntimeException("L'intervention ne possède pas de status, méthode updateItem dans la classe InterventionCell");
                    }          
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    }
}
