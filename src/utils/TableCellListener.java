// TableCellListener listens for changes in specific cells of a JTable and triggers a callback function, provided as a BiConsumer, with the updated cell's row index and value.
package utils;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.function.BiConsumer;

public class TableCellListener implements TableModelListener {
    private JTable table;
    private BiConsumer<Integer, String> cellChangeListener; // Change Consumer to BiConsumer
    private int columnIndex;

    public TableCellListener(JTable table, int columnIndex, BiConsumer<Integer, String> cellChangeListener) { // Change Consumer to BiConsumer
        this.table = table;
        this.cellChangeListener = cellChangeListener;
        this.table.getModel().addTableModelListener(this);
        this.columnIndex = columnIndex;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();

        if (e.getType() == TableModelEvent.UPDATE && column == columnIndex) {
            TableModel model = (TableModel) e.getSource();
            String columnName = model.getColumnName(column);
            String cellValue = String.valueOf(model.getValueAt(row, column));
            System.out.println(columnName);
            cellChangeListener.accept(row, cellValue);
        }
    }
}