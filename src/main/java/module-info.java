module biz.ei6.interventions.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.net.http;
   requires com.fasterxml.jackson.databind;
   
    opens biz.ei6.interventions.desktop to javafx.fxml;
    exports biz.ei6.interventions.desktop;
    exports biz.ei6.interventions.desktop.lib.domain;
}
