package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.framework.medias.WSMediaFilesDataSource;
import biz.ei6.interventions.desktop.framework.medias.WSMediasDataSource;
import biz.ei6.interventions.desktop.lib.interactors.UpdateClient;
import biz.ei6.interventions.desktop.framework.clients.WSClientsDataSource;
import biz.ei6.interventions.desktop.framework.interventions.WSInterventionsDataSource;
import biz.ei6.interventions.desktop.lib.data.ClientsDataSource;
import biz.ei6.interventions.desktop.lib.data.ClientsRepository;
import biz.ei6.interventions.desktop.lib.data.InterventionsDataSource;
import biz.ei6.interventions.desktop.lib.data.InterventionsRepository;
import biz.ei6.interventions.desktop.lib.data.MediaFilesDataSource;
import biz.ei6.interventions.desktop.lib.data.MediaFilesRepository;
import biz.ei6.interventions.desktop.lib.data.MediasDataSource;
import biz.ei6.interventions.desktop.lib.data.MediasRepository;
import biz.ei6.interventions.desktop.lib.interactors.AddClient;
import biz.ei6.interventions.desktop.lib.interactors.AddIntervention;
import biz.ei6.interventions.desktop.lib.interactors.AddMediaFile;
import biz.ei6.interventions.desktop.lib.interactors.GetClient;
import biz.ei6.interventions.desktop.lib.interactors.GetClients;
import biz.ei6.interventions.desktop.lib.interactors.GetInterventionMedias;
import biz.ei6.interventions.desktop.lib.interactors.GetInterventions;
import biz.ei6.interventions.desktop.lib.interactors.GetMedia;
import biz.ei6.interventions.desktop.lib.interactors.GetMediaFile;
import biz.ei6.interventions.desktop.lib.interactors.GetMedias;
import biz.ei6.interventions.desktop.lib.interactors.RemoveClient;
import biz.ei6.interventions.desktop.lib.interactors.RemoveIntervention;
import biz.ei6.interventions.desktop.lib.interactors.RemoveMedia;
import biz.ei6.interventions.desktop.lib.interactors.UpdateIntervention;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import javafx.scene.image.Image;

/*
 * JavaFX App
 */
public class App extends Application {

    static public class Interactors {

        ResourceBundle resources = ResourceBundle.getBundle("main");

        public InterventionsDataSource interventionsDataSource = new WSInterventionsDataSource(resources);//new MemoryDataSource();
        public InterventionsRepository interventionsRepository = new InterventionsRepository(interventionsDataSource);
        public AddIntervention addIntervention = new AddIntervention(interventionsRepository);
        public UpdateIntervention updateIntervention = new UpdateIntervention(interventionsRepository);
        public GetInterventions getInterventions = new GetInterventions(interventionsRepository);
        public RemoveIntervention removeIntervention = new RemoveIntervention(interventionsRepository);

        public ClientsDataSource clientsDataSource = new WSClientsDataSource(resources);
        public ClientsRepository clientsRepository = new ClientsRepository(clientsDataSource);
        public AddClient addClient = new AddClient(clientsRepository);
        public UpdateClient updateClient = new UpdateClient(clientsRepository);
        public GetClients getClients = new GetClients(clientsRepository);
        public GetClient getClient = new GetClient(clientsRepository);
        public RemoveClient removeClient = new RemoveClient(clientsRepository);
        
        public MediasDataSource mediasDataSource = new WSMediasDataSource(resources);
        public MediasRepository mediasRepository = new MediasRepository(mediasDataSource);
        public GetMedias getMedias = new GetMedias(mediasRepository);
        public GetInterventionMedias getInterventionMedias = new GetInterventionMedias(mediasRepository);
        public GetMedia getMedia = new GetMedia(mediasRepository);
        public RemoveMedia removeMedia = new RemoveMedia(mediasRepository);
        
        
        public MediaFilesDataSource mediaFileDataSource = new WSMediaFilesDataSource(resources);
        public MediaFilesRepository mediaFilesRepository = new MediaFilesRepository(mediaFileDataSource);
        public AddMediaFile addMediaFile = new AddMediaFile(mediaFilesRepository);
        public GetMediaFile getMediaFile = new GetMediaFile(mediaFilesRepository);
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

        scene = new Scene(root, 1220, 940);
        stage.setTitle(mainBundle.getString("titreAppli"));
        stage.setScene(scene);
        InputStream logo = getClass().getResourceAsStream("logo.png");
        stage.getIcons().add(new Image(logo));
        stage.setMinHeight(300);
        stage.setMinWidth(750);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
