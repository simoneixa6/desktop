module biz.ei6.interventions.desktop {
    requires javafx.controls;
    requires javafx.fxml;

    opens biz.ei6.interventions.desktop to javafx.fxml;
    exports biz.ei6.interventions.desktop;
}
