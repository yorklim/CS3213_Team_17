package sqlancer.common.gen;

import sqlancer.SQLGlobalState;
import sqlancer.common.schema.AbstractRelationalTable;
import sqlancer.common.schema.AbstractTableColumn;
import sqlancer.common.schema.TableIndex;

import java.util.List;

public abstract class PnYSQLTable<C extends AbstractTableColumn<?, ?>, I extends TableIndex, G extends SQLGlobalState<?, ?>>
        extends AbstractRelationalTable<C, I, G> {
    public PnYSQLTable(String name, List<C> columns, List<I> indexes, boolean isView) {
        super(name, columns, indexes, isView);
    }

    public abstract List<? extends PnYStatisticsObject> getStatistics();
}
