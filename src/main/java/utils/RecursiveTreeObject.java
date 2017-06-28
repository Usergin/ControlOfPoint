package utils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeTableColumn;

/**
 * Created by oldman on 28.06.17.
 */
public class RecursiveTreeObject <T> {

    /**
     * grouped children objects
     */
    ObservableList<T> children = FXCollections.observableArrayList();

    public ObservableList<T> getChildren() {
        return children;
    }

    public void setChildren(ObservableList<T> children) {
        this.children = children;
    }

    /**
     * Whether or not the object is grouped by a specified tree table column
     */
    ObjectProperty<TreeTableColumn<T, ?>> groupedColumn = new SimpleObjectProperty<>();

    public final ObjectProperty<TreeTableColumn<T, ?>> groupedColumnProperty() {
        return this.groupedColumn;
    }

    public final TreeTableColumn<T, ?> getGroupedColumn() {
        return this.groupedColumnProperty().get();
    }

    public final void setGroupedColumn(final TreeTableColumn<T, ?> groupedColumn) {
        this.groupedColumnProperty().set(groupedColumn);
    }

    /**
     * the value that must be shown when grouped
     */
    ObjectProperty<Object> groupedValue = new SimpleObjectProperty<>();

    public final ObjectProperty<Object> groupedValueProperty() {
        return this.groupedValue;
    }

    public final java.lang.Object getGroupedValue() {
        return this.groupedValueProperty().get();
    }

    public final void setGroupedValue(final java.lang.Object groupedValue) {
        this.groupedValueProperty().set(groupedValue);
    }


}
