/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.App.Interactors;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

/**
 *
 * @author Eixa6
 */
class ClientsController implements Initializable, DesktopListener  {

    Interactors interactors;

    public void setInteractors(App.Interactors interactors) {
        this.interactors = interactors;
    }

    @Override
    public void close() {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
