module biz.ei6.interventions.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.net.http;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;

   
    opens biz.ei6.interventions.desktop to javafx.fxml;
    opens biz.ei6.interventions.desktop.interventions to javafx.fxml;
    opens biz.ei6.interventions.desktop.clients to javafx.fxml;
    opens biz.ei6.interventions.desktop.restitutions to javafx.fxml;
    
    exports biz.ei6.interventions.desktop;
    exports biz.ei6.interventions.desktop.lib.domain;
    exports biz.ei6.interventions.desktop.framework.interventions;
    exports biz.ei6.interventions.desktop.framework.clients;
    exports biz.ei6.interventions.desktop.framework.medias;
    exports biz.ei6.interventions.desktop.interventions;
    exports biz.ei6.interventions.desktop.clients;
}
