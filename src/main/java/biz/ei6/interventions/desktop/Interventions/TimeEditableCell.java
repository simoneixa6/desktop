package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.lib.domain.Period;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

/**
 *
 * @author Eixa6
 */
public class TimeEditableCell extends TableCell<Period, LocalTime> {

    TableColumn<Period, LocalTime> column;
    
    DateTimeFormatter Timeformatter1 = DateTimeFormatter.ofPattern("H");   
    DateTimeFormatter Timeformatter2 = DateTimeFormatter.ofPattern("HH");
    DateTimeFormatter Timeformatter3 = DateTimeFormatter.ofPattern("Hmm");    
    DateTimeFormatter Timeformatter4 = DateTimeFormatter.ofPattern("HHmm");    
    DateTimeFormatter Timeformatter5 = DateTimeFormatter.ofPattern("HH:mm");


    

    private TextField textField;

    TimeEditableCell(TableColumn<Period, LocalTime> column) {
        this.column = column;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.requestFocus();
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        if (getItem() != null) {
            setText(getItem().format(Timeformatter5));
        }
        setGraphic(null);
    }

    @Override
    protected void updateItem(LocalTime time, boolean empty) {
        super.updateItem(time, empty);
        if (empty || time == null) {
            setText("");
        } else {
            setText(Timeformatter5.format(time));
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0,
                Boolean arg1, Boolean arg2) -> {
            if (!arg2) {
                // Essaye de parser la valeur en LocalTime, si exception alors la valeur ne correspond pas au bon format, on annule l'édition
                try {
                    LocalTime parsedTime;
                    switch(textField.getText().length()){
                        case 1:
                            parsedTime = LocalTime.parse(textField.getText(), Timeformatter1);
                            break;
                        case 2:
                            parsedTime = LocalTime.parse(textField.getText(), Timeformatter2);
                            break;
                        case 3:
                            parsedTime = LocalTime.parse(textField.getText(), Timeformatter3);
                            break;
                        case 4:
                            parsedTime = LocalTime.parse(textField.getText(), Timeformatter4);
                            break;
                        case 5:
                            parsedTime = LocalTime.parse(textField.getText(), Timeformatter5);
                        default:
                            throw new Exception();
                    }
                    commitEdit(parsedTime);
                    setGraphic(null);
                } catch (Exception e) {
                    cancelEdit();
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().format(Timeformatter5);
    }
}
