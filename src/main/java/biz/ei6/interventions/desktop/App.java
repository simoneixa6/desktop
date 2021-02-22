package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.framework.MemoryDataSource;
import biz.ei6.interventions.desktop.framework.WSDataSource;
import biz.ei6.interventions.desktop.lib.data.InterventionsDataSource;
import biz.ei6.interventions.desktop.lib.data.InterventionsRepository;
import biz.ei6.interventions.desktop.lib.interactors.AddIntervention;
import biz.ei6.interventions.desktop.lib.interactors.GetInterventions;
import biz.ei6.interventions.desktop.lib.interactors.RemoveIntervention;
import biz.ei6.interventions.desktop.lib.interactors.UpdateIntervention;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/*
 * JavaFX App
 */
public class App extends Application {

    static class Interactors {
        InterventionsDataSource interventionsDataSource = new WSDataSource();//new MemoryDataSource();
        InterventionsRepository interventionsRepository = new InterventionsRepository(interventionsDataSource);
        AddIntervention addIntervention = new AddIntervention(interventionsRepository);
        UpdateIntervention updateIntervention = new UpdateIntervention(interventionsRepository);
        GetInterventions getInterventions = new GetInterventions(interventionsRepository);
        RemoveIntervention removeIntervention = new RemoveIntervention(interventionsRepository);
    }

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Interactors interactors = new Interactors();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
        Parent root = fxmlLoader.load();
        var ctrl = (MainController) fxmlLoader.getController();
        ctrl.setInteractors(interactors);

        scene = new Scene(root, 1200, 940);
        stage.setTitle("Gestion des interventions");
        stage.setScene(scene);
        stage.setMinHeight(300);
        stage.setMinWidth(750);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
