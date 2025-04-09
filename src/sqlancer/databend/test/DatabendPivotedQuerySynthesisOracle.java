package sqlancer.databend.test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import sqlancer.Randomly;
import sqlancer.SQLConnection;
import sqlancer.common.oracle.PivotedQuerySynthesisBase;
import sqlancer.common.oracle.PivotedQuerySynthesisOracleCommon;
import sqlancer.common.query.Query;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.databend.DatabendErrors;
import sqlancer.databend.DatabendExpectedValueVisitor;
import sqlancer.databend.DatabendProvider.DatabendGlobalState;
import sqlancer.databend.DatabendSchema.DatabendColumn;
import sqlancer.databend.DatabendSchema.DatabendDataType;
import sqlancer.databend.DatabendSchema.DatabendRowValue;
import sqlancer.databend.DatabendSchema.DatabendTables;
import sqlancer.databend.DatabendToStringVisitor;
import sqlancer.databend.ast.DatabendColumnValue;
import sqlancer.databend.ast.DatabendConstant;
import sqlancer.databend.ast.DatabendExpression;
import sqlancer.databend.ast.DatabendSelect;
import sqlancer.databend.ast.DatabendTableReference;
import sqlancer.databend.ast.DatabendUnaryPostfixOperation;
import sqlancer.databend.ast.DatabendUnaryPrefixOperation;
import sqlancer.databend.gen.DatabendNewExpressionGenerator;

public class DatabendPivotedQuerySynthesisOracle
        extends PivotedQuerySynthesisBase<DatabendGlobalState, DatabendRowValue, DatabendExpression, SQLConnection> {

    private List<DatabendColumn> fetchColumns;

    public DatabendPivotedQuerySynthesisOracle(DatabendGlobalState globalState) {
        super(globalState);
        DatabendErrors.addExpressionErrors(errors);
        DatabendErrors.addInsertErrors(errors);
    }

    @Override
    protected Query<SQLConnection> getRectifiedQuery() throws Exception {
        DatabendTables randomTables = globalState.getSchema().getRandomTableNonEmptyAndViewTables();
        List<DatabendColumn> columns = randomTables.getColumns();
        DatabendSelect selectStatement = new DatabendSelect();
        boolean isDistinct = Randomly.getBoolean();
        selectStatement.setDistinct(isDistinct);
        pivotRow = randomTables.getRandomRowValue(globalState.getConnection());
        fetchColumns = columns;
        selectStatement.setFetchColumns(fetchColumns.stream()
                .map(c -> new DatabendColumnValue(getFetchValueAliasedColumn(c), pivotRow.getValues().get(c)))
                .collect(Collectors.toList()));
        selectStatement.setFromList(
                randomTables.getTables().stream().map(DatabendTableReference::new).collect(Collectors.toList()));
        DatabendExpression whereClause = generateRectifiedExpression(columns, pivotRow);
        selectStatement.setWhereClause(whereClause);
        List<DatabendExpression> groupByClause = generateGroupByClause(columns, pivotRow);
        selectStatement.setGroupByExpressions(groupByClause);
        DatabendExpression limitClause = generateLimit();
        selectStatement.setLimitClause(limitClause);
        if (limitClause != null) {
            DatabendExpression offsetClause = generateOffset();
            selectStatement.setOffsetClause(offsetClause);
        }
        DatabendNewExpressionGenerator gen = new DatabendNewExpressionGenerator(globalState).setColumns(columns);
        if (!isDistinct) {
            List<DatabendExpression> orderBys = gen.generateOrderBy();
            selectStatement.setOrderByClauses(orderBys);
        }
        return new SQLQueryAdapter(DatabendToStringVisitor.asString(selectStatement), errors);
    }

    private DatabendExpression generateRectifiedExpression(List<DatabendColumn> columns, DatabendRowValue pivotRow) {
        DatabendNewExpressionGenerator gen = new DatabendNewExpressionGenerator(globalState).setColumns(columns);
        gen.setRowValue(pivotRow);
        DatabendExpression expr = gen.generateExpressionWithExpectedResult(DatabendDataType.BOOLEAN);
        DatabendExpression result = null;
        if (expr.getExpectedValue().isNull()) {
            result = new DatabendUnaryPostfixOperation(expr,
                    DatabendUnaryPostfixOperation.DatabendUnaryPostfixOperator.IS_NULL);
        } else if (!expr.getExpectedValue().cast(DatabendDataType.BOOLEAN).asBoolean()) {
            result = new DatabendUnaryPrefixOperation(expr,
                    DatabendUnaryPrefixOperation.DatabendUnaryPrefixOperator.NOT);
        }
        rectifiedPredicates.add(result);
        return result;
    }

    @Override
    protected Query<SQLConnection> getContainmentCheckQuery(Query<?> pivotRowQuery) throws Exception {
        String resultingQueryString = PivotedQuerySynthesisOracleCommon.getContainmentCheckQueryCommon(fetchColumns,
                pivotRow, pivotRowQuery);
        return new SQLQueryAdapter(resultingQueryString, errors);
    }

    private DatabendColumn getFetchValueAliasedColumn(DatabendColumn c) {
        DatabendColumn aliasedColumn = new DatabendColumn(c.getName() + " AS " + c.getTable().getName() + c.getName(),
                c.getType(), false, false);
        aliasedColumn.setTable(c.getTable());
        return aliasedColumn;
    }

    @Override
    protected String getExpectedValues(DatabendExpression expr) {
        return DatabendExpectedValueVisitor.asExpectedValues(expr);
    }

    private List<DatabendExpression> generateGroupByClause(List<DatabendColumn> columns, DatabendRowValue rowValue) {
        if (Randomly.getBoolean()) {
            return columns.stream().map(c -> new DatabendColumnValue(c, rowValue.getValues().get(c)))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private DatabendExpression generateLimit() {
        if (Randomly.getBoolean()) {
            return DatabendConstant.createIntConstant(Integer.MAX_VALUE);
        } else {
            return null;
        }
    }

    private DatabendExpression generateOffset() {
        if (Randomly.getBoolean()) {
            return DatabendConstant.createIntConstant(0);
        } else {
            return null;
        }
    }

}
