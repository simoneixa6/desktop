package biz.ei6.interventions.desktop.interventions;

import biz.ei6.interventions.desktop.lib.domain.Period;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

/**
 *
 * @author Eixa6
 */
public class DateEditableCell extends TableCell<Period, LocalDate> {

    TableColumn<Period, LocalDate> column;
    private TextField textField;
    DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    DateEditableCell(TableColumn<Period, LocalDate> column) {
        this.column = column;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        if(getItem()!=null){
            setText(getItem().format(Dateformatter));     
        }
        setGraphic(null);
    }

    @Override
    protected void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        if (empty || date == null) {
            setText("");
        } else {
            setText(Dateformatter.format(date));
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> arg0,
                        Boolean arg1, Boolean arg2) -> {
                    if (!arg2) {
                        // Essaye de parser la valeur en LocalDate, si exception alors la valeur ne correspond pas au bon format, on annule l'édition
                        try {
                            LocalDate parsedDate = LocalDate.parse(textField.getText(), Dateformatter);
                            commitEdit(parsedDate);
                            setGraphic(null);
                        } catch (Exception e) {
                            cancelEdit();
                        }
                    }
                });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().format(Dateformatter);
    }

}
