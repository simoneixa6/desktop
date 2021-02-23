/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.io.IOException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Eixa6
 */
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
                
                lblIntervention.setText(intervention.getTitle() + " - " + intervention.getKm());
                
                Image greenDot = new Image("file:///C:/Users/Eixa6/Documents/NetBeansProjects/desktop/src/main/resources/biz/ei6/interventions/desktop/assets/green.png");
                Image orangeDot = new Image("file:///C:\\Users\\Eixa6\\Documents\\NetBeansProjects\\desktop\\src\\main\\resources\\biz\\ei6\\interventions\\desktop\\assets\\orange.png");
                Image yellowDot = new Image("file:///C:\\Users\\Eixa6\\Documents\\NetBeansProjects\\desktop\\src\\main\\resources\\biz\\ei6\\interventions\\desktop\\assets\\yellow.png");
                Image redDot = new Image("file:///C:\\Users\\Eixa6\\Documents\\NetBeansProjects\\desktop\\src\\main\\resources\\biz\\ei6\\interventions\\desktop\\assets\\red.png");

                    switch(intervention.getStatus()){
                        case "Ouverte":
                            status.setImage(redDot);  
                            break;
                        case "Terminée":
                            status.setImage(yellowDot);
                            break;
                        case "Facturée":
                            status.setImage(orangeDot);
                            break;
                        case "Réglée":
                            status.setImage(greenDot);
                            break;
                        default:
                            status.setImage(redDot);
                            break;
                    }
             
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
}
