package sqlancer.presto.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import sqlancer.Randomly;
import sqlancer.presto.PrestoGlobalState;
import sqlancer.presto.PrestoSchema;
import sqlancer.presto.PrestoSchema.PrestoTable;
import sqlancer.presto.PrestoSchema.PrestoTables;
import sqlancer.presto.ast.PrestoConstant;
import sqlancer.presto.ast.PrestoExpression;
import sqlancer.presto.ast.PrestoJoin;
import sqlancer.presto.ast.PrestoSelect;
import sqlancer.presto.ast.PrestoTableReference;

public final class PrestoRandomQuerySynthesizer {

    private PrestoRandomQuerySynthesizer() {
    }

    public static PrestoSelect generateSelect(PrestoGlobalState globalState, int nrColumns) {
        PrestoTables targetTables = globalState.getSchema().getRandomTableNonEmptyTables();
        PrestoTypedExpressionGenerator gen = new PrestoTypedExpressionGenerator(globalState)
                .setColumns(targetTables.getColumns());
        PrestoSelect select = new PrestoSelect();
        // TODO: distinct
        List<PrestoExpression> columns = new ArrayList<>();
        for (int i = 0; i < nrColumns; i++) {
            PrestoExpression expression = gen
                    .generateExpression(PrestoSchema.PrestoCompositeDataType.getRandomWithoutNull());
            columns.add(expression);
        }
        select.setFetchColumns(columns);
        List<PrestoTable> tables = targetTables.getTables();
        List<PrestoTableReference> tableList = tables.stream().map(t -> new PrestoTableReference(t))
                .collect(Collectors.toList());
        List<PrestoExpression> joins = PrestoJoin.getJoins(tableList, globalState).stream()
                .collect(Collectors.toList());
        select.setJoinList(new ArrayList<>(joins));
        select.setFromList(new ArrayList<>(tableList));
        if (Randomly.getBoolean()) {
            select.setWhereClause(gen.generateExpression(PrestoSchema.PrestoCompositeDataType.getRandomWithoutNull()));
        }
        if (Randomly.getBoolean()) {
            select.setOrderByClauses(gen.generateOrderBys());
        }
        if (Randomly.getBoolean()) {
            select.setGroupByExpressions(gen.generateExpressions(Randomly.smallNumber() + 1));
        }

        if (Randomly.getBoolean()) {
            select.setLimitClause(PrestoConstant.createIntConstant(Randomly.getNotCachedInteger(0, Integer.MAX_VALUE)));
        }
        if (Randomly.getBoolean()) {
            select.setHavingClause(gen.generateHavingClause());
        }
        return select;
    }

}
