package sqlancer.postgres.oracle.tlp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.postgresql.util.PSQLException;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.oracle.AggregateOracleCommon;
import sqlancer.common.oracle.TestOracle;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.common.query.SQLancerResultSet;
import sqlancer.postgres.PostgresGlobalState;
import sqlancer.postgres.PostgresSchema.PostgresDataType;
import sqlancer.postgres.PostgresVisitor;
import sqlancer.postgres.ast.PostgresAggregate;
import sqlancer.postgres.ast.PostgresAggregate.PostgresAggregateFunction;
import sqlancer.postgres.ast.PostgresAlias;
import sqlancer.postgres.ast.PostgresExpression;
import sqlancer.postgres.ast.PostgresJoin;
import sqlancer.postgres.ast.PostgresPostfixOperation;
import sqlancer.postgres.ast.PostgresPostfixOperation.PostfixOperator;
import sqlancer.postgres.ast.PostgresPrefixOperation;
import sqlancer.postgres.ast.PostgresPrefixOperation.PrefixOperator;
import sqlancer.postgres.ast.PostgresSelect;
import sqlancer.postgres.gen.PostgresCommon;

public class PostgresTLPAggregateOracle extends PostgresTLPBase implements TestOracle<PostgresGlobalState> {

    private String firstResult;
    private String secondResult;
    private String originalQuery;
    private String metamorphicQuery;

    public PostgresTLPAggregateOracle(PostgresGlobalState state) {
        super(state);
        PostgresCommon.addGroupingErrors(errors);
    }

    @Override
    public void check() throws SQLException {
        super.check();
        aggregateCheck();
    }

    protected void aggregateCheck() throws SQLException {
        PostgresAggregateFunction aggregateFunction = Randomly.fromOptions(PostgresAggregateFunction.MAX,
                PostgresAggregateFunction.MIN, PostgresAggregateFunction.SUM, PostgresAggregateFunction.BIT_AND,
                PostgresAggregateFunction.BIT_OR, PostgresAggregateFunction.BOOL_AND, PostgresAggregateFunction.BOOL_OR,
                PostgresAggregateFunction.COUNT);
        PostgresAggregate aggregate = gen.generateArgsForAggregate(aggregateFunction.getRandomReturnType(),
                aggregateFunction);
        List<PostgresExpression> fetchColumns = new ArrayList<>();
        fetchColumns.add(aggregate);
        while (Randomly.getBooleanWithRatherLowProbability()) {
            fetchColumns.add(gen.generateAggregate());
        }
        select.setFetchColumns(Arrays.asList(aggregate));
        if (Randomly.getBooleanWithRatherLowProbability()) {
            select.setOrderByClauses(gen.generateOrderBys());
        }
        originalQuery = PostgresVisitor.asString(select);
        firstResult = getAggregateResult(originalQuery);
        metamorphicQuery = createMetamorphicUnionQuery(select, aggregate, select.getFromList());
        secondResult = getAggregateResult(metamorphicQuery);

        Map<String, String> map = new HashMap<>();
        map.put("firstResult", firstResult);
        map.put("secondResult", secondResult);
        map.put("originalQuery", originalQuery);
        map.put("metamorphicQuery", metamorphicQuery);
        AggregateOracleCommon.aggregateCheckCommon(state, map);
    }

    private String createMetamorphicUnionQuery(PostgresSelect select, PostgresAggregate aggregate,
            List<PostgresExpression> from) {
        String metamorphicQuery;
        PostgresExpression whereClause = gen.generateExpression(PostgresDataType.BOOLEAN);
        PostgresExpression negatedClause = new PostgresPrefixOperation(whereClause, PrefixOperator.NOT);
        PostgresExpression notNullClause = new PostgresPostfixOperation(whereClause, PostfixOperator.IS_NULL);
        List<PostgresExpression> mappedAggregate = mapped(aggregate);
        PostgresSelect leftSelect = getSelect(mappedAggregate, from, whereClause, select.getJoinClauses());
        PostgresSelect middleSelect = getSelect(mappedAggregate, from, negatedClause, select.getJoinClauses());
        PostgresSelect rightSelect = getSelect(mappedAggregate, from, notNullClause, select.getJoinClauses());
        metamorphicQuery = "SELECT " + getOuterAggregateFunction(aggregate) + " FROM (";
        metamorphicQuery += PostgresVisitor.asString(leftSelect) + " UNION ALL "
                + PostgresVisitor.asString(middleSelect) + " UNION ALL " + PostgresVisitor.asString(rightSelect);
        metamorphicQuery += ") as asdf";
        return metamorphicQuery;
    }

    private String getAggregateResult(String queryString) throws SQLException {
        // log TLP Aggregate SELECT queries on the current log file
        if (state.getOptions().logEachSelect()) {
            // TODO: refactor me
            state.getLogger().writeCurrent(queryString);
            try {
                state.getLogger().getCurrentFileWriter().flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        String resultString;
        SQLQueryAdapter q = new SQLQueryAdapter(queryString, errors);
        try (SQLancerResultSet result = q.executeAndGet(state)) {
            if (result == null) {
                throw new IgnoreMeException();
            }
            if (!result.next()) {
                resultString = null;
            } else {
                resultString = result.getString(1);
            }
        } catch (PSQLException e) {
            throw new AssertionError(queryString, e);
        }
        return resultString;
    }

    private List<PostgresExpression> mapped(PostgresAggregate aggregate) {
        switch (aggregate.getFunction()) {
        case SUM:
        case COUNT:
        case BIT_AND:
        case BIT_OR:
        case BOOL_AND:
        case BOOL_OR:
        case MAX:
        case MIN:
            return aliasArgs(Arrays.asList(aggregate));
        default:
            throw new AssertionError(aggregate.getFunction());
        }
    }

    private List<PostgresExpression> aliasArgs(List<PostgresExpression> originalAggregateArgs) {
        List<PostgresExpression> args = new ArrayList<>();
        int i = 0;
        for (PostgresExpression expr : originalAggregateArgs) {
            args.add(new PostgresAlias(expr, "agg" + i++));
        }
        return args;
    }

    private String getOuterAggregateFunction(PostgresAggregate aggregate) {
        switch (aggregate.getFunction()) {
        case COUNT:
            return PostgresAggregateFunction.SUM.toString() + "(agg0)";
        default:
            return aggregate.getFunction().toString() + "(agg0)";
        }
    }

    private PostgresSelect getSelect(List<PostgresExpression> aggregates, List<PostgresExpression> from,
            PostgresExpression whereClause, List<PostgresJoin> joinList) {
        PostgresSelect leftSelect = new PostgresSelect();
        leftSelect.setFetchColumns(aggregates);
        leftSelect.setFromList(from);
        leftSelect.setWhereClause(whereClause);
        leftSelect.setJoinClauses(joinList);
        if (Randomly.getBooleanWithSmallProbability()) {
            leftSelect.setGroupByExpressions(gen.generateExpressions(Randomly.smallNumber() + 1));
        }
        return leftSelect;
    }

}
