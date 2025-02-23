package sqlancer.common.oracle;

import java.util.List;

import sqlancer.common.query.Query;
import sqlancer.common.schema.AbstractRowValue;
import sqlancer.common.schema.AbstractTableColumn;

public final class PivotedQuerySynthesisOracleCommon {

    private PivotedQuerySynthesisOracleCommon() {
    }

    public static String getContainmentCheckQueryCommon(List<? extends AbstractTableColumn<?, ?>> fetchColumns,
            AbstractRowValue<?, ?, ?> pivotRow, Query<?> pivotRowQuery) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM (");
        sb.append(pivotRowQuery.getUnterminatedQueryString());
        sb.append(") as result WHERE ");
        int i = 0;
        for (AbstractTableColumn<?, ?> c : fetchColumns) {
            if (i++ != 0) {
                sb.append(" AND ");
            }
            sb.append("result.");
            sb.append(c.getTable().getName());
            sb.append(c.getName());
            if (pivotRow.getValues().get(c) == null) {
                sb.append(" IS NULL ");
            } else {
                sb.append(" = ");
                sb.append(pivotRow.getValues().get(c).toString());
            }
        }
        return sb.toString();
    }

}
