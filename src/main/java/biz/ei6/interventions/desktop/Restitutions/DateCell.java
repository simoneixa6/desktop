package biz.ei6.interventions.desktop.restitutions;

import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

/**
 *
 * @author Eixa6
 */
public class DateCell extends TableCell<Intervention, LocalDate> {

    
    TableColumn<Intervention, LocalDate> column;
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
