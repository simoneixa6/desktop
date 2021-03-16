package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.clients.ClientsPane;
import biz.ei6.interventions.desktop.interventions.InterventionsPane;
import biz.ei6.interventions.desktop.App.Interactors;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/*
 * @author Eixa6
 */
public class MainController implements Initializable {

    @FXML
    BorderPane mainBorderPane;

    @FXML
    Button interventionsBtn;

    @FXML
    Button clientsBtn;

    @FXML
    Button restitutionsBtn;

    @FXML
    Button usersBtn;

    @FXML
    Button rolesBtn;

    Interactors interactors;

    InterventionsPane interventionsPane;
    ClientsPane clientsPane;
    RestitutionsPane restitutionsPane;

    public void setInteractors(Interactors interactors) {
        this.interactors = interactors;
    }

    public void setDefaultPane() {
        // Intervention pane affiché par défault au démmarage
        interventionsPane = new InterventionsPane(interactors);
        mainBorderPane.setCenter(interventionsPane);
        setSelectedButtonStyle(interventionsBtn);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        /*
         * Action sur le clic du bouton "Interventions"
         */
        interventionsBtn.setOnAction((ActionEvent actionEvent) -> {
            if (mainBorderPane.getCenter() != interventionsPane) {
                interventionsPane = new InterventionsPane(interactors);
                mainBorderPane.setCenter(interventionsPane);
                setSelectedButtonStyle(interventionsBtn);
            }
        });

        /*
         * Action sur le clic du bouton "Clients"
         */
        clientsBtn.setOnAction((ActionEvent actionEvent) -> {
            if (mainBorderPane.getCenter() != clientsPane) {
                clientsPane = new ClientsPane(interactors);
                mainBorderPane.setCenter(clientsPane);
                setSelectedButtonStyle(clientsBtn);
            }
        });

        /*
         * Action sur le clic du bouton "Restitution"
         */
        restitutionsBtn.setOnAction((ActionEvent actionEvent) -> {
            if (mainBorderPane.getCenter() != restitutionsPane) {
                restitutionsPane = new RestitutionsPane(interactors);
                mainBorderPane.setCenter(restitutionsPane);
                setSelectedButtonStyle(restitutionsBtn);
            }
        });
    }

    public void setSelectedButtonStyle(Button button) {
        button.setId("sidebarBtnSelected");

        if (button != interventionsBtn) {
            interventionsBtn.setId("sidebarBtn");
        }
        if (button != clientsBtn) {
            clientsBtn.setId("sidebarBtn");
        }
        if (button != restitutionsBtn) {
            restitutionsBtn.setId("sidebarBtn");
        }
        if (button != usersBtn) {
            usersBtn.setId("sidebarBtn");
        }
        if (button != rolesBtn) {
            rolesBtn.setId("sidebarBtn");
        }
    }
}
