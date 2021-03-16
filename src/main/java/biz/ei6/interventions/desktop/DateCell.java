package biz.ei6.interventions.desktop;

import biz.ei6.interventions.desktop.lib.domain.Intervention;
import biz.ei6.interventions.desktop.lib.domain.Period;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

/**
 *
 * @author Eixa6
 */
public class DateCell extends TableCell<Intervention, LocalDate> {

    TableColumn<Intervention, LocalDate> column;
    private TextField textField;
    DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    DateCell(TableColumn<Intervention, LocalDate> column) {
        this.column = column;
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
}
