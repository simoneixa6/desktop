package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.lib.interactors.UpdateClient;
import biz.ei6.interventions.desktop.framework.clients.WSClientsDataSource;
import biz.ei6.interventions.desktop.framework.interventions.WSInterventionsDataSource;
import biz.ei6.interventions.desktop.lib.data.ClientsDataSource;
import biz.ei6.interventions.desktop.lib.data.ClientsRepository;
import biz.ei6.interventions.desktop.lib.data.InterventionsDataSource;
import biz.ei6.interventions.desktop.lib.data.InterventionsRepository;
import biz.ei6.interventions.desktop.lib.interactors.AddClient;
import biz.ei6.interventions.desktop.lib.interactors.AddIntervention;
import biz.ei6.interventions.desktop.lib.interactors.GetClients;
import biz.ei6.interventions.desktop.lib.interactors.GetInterventions;
import biz.ei6.interventions.desktop.lib.interactors.RemoveClient;
import biz.ei6.interventions.desktop.lib.interactors.RemoveIntervention;
import biz.ei6.interventions.desktop.lib.interactors.UpdateIntervention;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ResourceBundle;

/*
 * JavaFX App
 */
public class App extends Application {

    static public class Interactors{
        
        ResourceBundle resources = ResourceBundle.getBundle("main");
        public InterventionsDataSource interventionsDataSource = new WSInterventionsDataSource(resources);//new MemoryDataSource();
        public InterventionsRepository interventionsRepository = new InterventionsRepository(interventionsDataSource);
        public AddIntervention addIntervention = new AddIntervention(interventionsRepository);
        public UpdateIntervention updateIntervention = new UpdateIntervention(interventionsRepository);
        public GetInterventions getInterventions = new GetInterventions(interventionsRepository);
        public RemoveIntervention removeIntervention = new RemoveIntervention(interventionsRepository);
        
        public ClientsDataSource clientsDataSource = new WSClientsDataSource();
        public ClientsRepository clientsRepository = new ClientsRepository(clientsDataSource);
        public AddClient addClient = new AddClient(clientsRepository);
        public UpdateClient updateClient = new UpdateClient(clientsRepository);
        public GetClients getClients = new GetClients(clientsRepository);
        public RemoveClient removeClient = new RemoveClient(clientsRepository);
    }

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        ResourceBundle mainBundle = ResourceBundle.getBundle("main");
        Interactors interactors = new Interactors();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"), mainBundle);
        MainController ctrl = new MainController();
        fxmlLoader.setController(ctrl);
        Parent root = fxmlLoader.load();
        ctrl.setInteractors(interactors);
        ctrl.setDefaultPane();

        scene = new Scene(root, 1200, 940);
        stage.setTitle(mainBundle.getString("titreAppli"));
        stage.setScene(scene);
        stage.setMinHeight(300);
        stage.setMinWidth(750);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
