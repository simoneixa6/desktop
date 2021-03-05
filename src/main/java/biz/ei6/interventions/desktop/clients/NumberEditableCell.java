package biz.ei6.interventions.desktop.clients;

import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import biz.ei6.interventions.desktop.lib.domain.Site;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;

/**
 *
 * @author Eixa6
 */
public class NumberEditableCell extends TableCell<Site, String> {

    TableColumn<Site, String> column;
    private TextField textField;

    NumberEditableCell(TableColumn<Site, String> column) {
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
        setText(getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> arg0,
                        Boolean arg1, Boolean arg2) -> {
                    if (!arg2) {
                        // Essaye de parser l'entrée utilisateur en un entier, si il y a une exception l'entrée était mauvaise, on annule l'édition
                        try {
                            Integer.parseInt(textField.getText());
                            commitEdit(textField.getText());
                            setGraphic(null);

                        } catch (NumberFormatException e) {
                            cancelEdit();
                        }
                    }
                });
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
