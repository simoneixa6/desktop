jpackage --type exe --input ./target --copyright Eixa6 --app-version 1.0.4 --main-jar interventions_desktop-1.0.4-jar-with-dependencies.jar -n interventions_desktop --module-path "%PATH_TO_FX_MODS%" --add-modules javafx.controls,javafx.fxml,java.xml,java.net.http --main-class biz.ei6.interventions.desktop.App --win-console --win-shortcut --icon logo.ico 