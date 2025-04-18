package sqlancer.databend.test.tlp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import sqlancer.Randomly;
import sqlancer.common.gen.ExpressionGenerator;
import sqlancer.common.oracle.TernaryLogicPartitioningOracleBase;
import sqlancer.common.oracle.TestOracle;
import sqlancer.databend.DatabendBugs;
import sqlancer.databend.DatabendErrors;
import sqlancer.databend.DatabendProvider.DatabendGlobalState;
import sqlancer.databend.DatabendSchema;
import sqlancer.databend.DatabendSchema.DatabendColumn;
import sqlancer.databend.DatabendSchema.DatabendTable;
import sqlancer.databend.DatabendSchema.DatabendTables;
import sqlancer.databend.ast.DatabendColumnReference;
import sqlancer.databend.ast.DatabendColumnValue;
import sqlancer.databend.ast.DatabendExpression;
import sqlancer.databend.ast.DatabendJoin;
import sqlancer.databend.ast.DatabendSelect;
import sqlancer.databend.ast.DatabendTableReference;
import sqlancer.databend.gen.DatabendNewExpressionGenerator;

public class DatabendQueryPartitioningBase
        extends TernaryLogicPartitioningOracleBase<DatabendExpression, DatabendGlobalState>
        implements TestOracle<DatabendGlobalState> {

    DatabendSchema s;
    DatabendTables targetTables;
    DatabendNewExpressionGenerator gen;
    DatabendSelect select;

    List<DatabendExpression> groupByExpression;

    public DatabendQueryPartitioningBase(DatabendGlobalState state) {
        super(state);
        DatabendErrors.addExpressionErrors(errors);
    }

    @Override
    public void check() throws SQLException {
        s = state.getSchema();
        targetTables = s.getRandomTableNonEmptyAndViewTables();
        List<DatabendColumn> randomColumn = targetTables.getColumns();

        gen = new DatabendNewExpressionGenerator(state).setColumns(targetTables.getColumns());
        HashSet<DatabendColumnValue> columnOfLeafNode = new HashSet<>();
        gen.setColumnOfLeafNode(columnOfLeafNode);
        initializeTernaryPredicateVariants();
        select = new DatabendSelect();
        columnOfLeafNode
                .addAll(randomColumn.stream().map(c -> new DatabendColumnValue(c, null)).collect(Collectors.toList()));
        groupByExpression = new ArrayList<>(columnOfLeafNode);

        select.setFetchColumns(randomColumn.stream().map(DatabendColumnReference::new).collect(Collectors.toList()));
        List<DatabendTable> tables = targetTables.getTables();
        List<DatabendTableReference> tableList = tables.stream().map(DatabendTableReference::new)
                .collect(Collectors.toList());
        if (!DatabendBugs.bug9236) {
            List<DatabendJoin> joins = DatabendJoin.getJoins(tableList, state);
            select.setJoinList(joins.stream().collect(Collectors.toList()));
        }
        select.setFromList(tableList.stream().collect(Collectors.toList()));
        select.setWhereClause(null);
    }

    List<DatabendExpression> generateFetchColumns() {
        List<DatabendExpression> columns = new ArrayList<>();
        if (Randomly.getBoolean()) {
            columns.add(new DatabendColumnReference(new DatabendColumn("*", null, false, false)));
        } else {
            columns = generateRandomColumns();
        }
        return columns;
    }

    List<DatabendExpression> generateRandomColumns() {
        List<DatabendExpression> columns;
        columns = Randomly.nonEmptySubset(targetTables.getColumns()).stream().map(DatabendColumnReference::new)
                .collect(Collectors.toList());
        return columns;
    }

    @Override
    protected ExpressionGenerator<DatabendExpression> getGen() {
        return gen;
    }

}
