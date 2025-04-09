package sqlancer.yugabyte.ysql.oracle.tlp;

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
import sqlancer.yugabyte.ysql.YSQLErrors;
import sqlancer.yugabyte.ysql.YSQLGlobalState;
import sqlancer.yugabyte.ysql.YSQLSchema.YSQLDataType;
import sqlancer.yugabyte.ysql.YSQLVisitor;
import sqlancer.yugabyte.ysql.ast.YSQLAggregate;
import sqlancer.yugabyte.ysql.ast.YSQLAggregate.YSQLAggregateFunction;
import sqlancer.yugabyte.ysql.ast.YSQLAlias;
import sqlancer.yugabyte.ysql.ast.YSQLExpression;
import sqlancer.yugabyte.ysql.ast.YSQLJoin;
import sqlancer.yugabyte.ysql.ast.YSQLPostfixOperation;
import sqlancer.yugabyte.ysql.ast.YSQLPostfixOperation.PostfixOperator;
import sqlancer.yugabyte.ysql.ast.YSQLPrefixOperation;
import sqlancer.yugabyte.ysql.ast.YSQLPrefixOperation.PrefixOperator;
import sqlancer.yugabyte.ysql.ast.YSQLSelect;

public class YSQLTLPAggregateOracle extends YSQLTLPBase implements TestOracle<YSQLGlobalState> {

    private String firstResult;
    private String secondResult;
    private String originalQuery;
    private String metamorphicQuery;

    public YSQLTLPAggregateOracle(YSQLGlobalState state) {
        super(state);
        YSQLErrors.addGroupingErrors(errors);
    }

    @Override
    public void check() throws SQLException {
        super.check();
        aggregateCheck();
    }

    protected void aggregateCheck() throws SQLException {
        YSQLAggregateFunction aggregateFunction = Randomly.fromOptions(YSQLAggregateFunction.MAX,
                YSQLAggregateFunction.MIN, YSQLAggregateFunction.SUM, YSQLAggregateFunction.BIT_AND,
                YSQLAggregateFunction.BIT_OR, YSQLAggregateFunction.BOOL_AND, YSQLAggregateFunction.BOOL_OR,
                YSQLAggregateFunction.COUNT);
        YSQLAggregate aggregate = gen.generateArgsForAggregate(aggregateFunction.getRandomReturnType(),
                aggregateFunction);
        List<YSQLExpression> fetchColumns = new ArrayList<>();
        fetchColumns.add(aggregate);
        while (Randomly.getBooleanWithRatherLowProbability()) {
            fetchColumns.add(gen.generateAggregate());
        }
        select.setFetchColumns(Arrays.asList(aggregate));
        if (Randomly.getBooleanWithRatherLowProbability()) {
            select.setOrderByClauses(gen.generateOrderBys());
        }
        originalQuery = YSQLVisitor.asString(select);
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

    private String createMetamorphicUnionQuery(YSQLSelect select, YSQLAggregate aggregate, List<YSQLExpression> from) {
        String metamorphicQuery;
        YSQLExpression whereClause = gen.generateExpression(YSQLDataType.BOOLEAN);
        YSQLExpression negatedClause = new YSQLPrefixOperation(whereClause, PrefixOperator.NOT);
        YSQLExpression notNullClause = new YSQLPostfixOperation(whereClause, PostfixOperator.IS_NULL);
        List<YSQLExpression> mappedAggregate = mapped(aggregate);
        YSQLSelect leftSelect = getSelect(mappedAggregate, from, whereClause, select.getJoinClauses());
        YSQLSelect middleSelect = getSelect(mappedAggregate, from, negatedClause, select.getJoinClauses());
        YSQLSelect rightSelect = getSelect(mappedAggregate, from, notNullClause, select.getJoinClauses());
        metamorphicQuery = "SELECT " + getOuterAggregateFunction(aggregate) + " FROM (";
        metamorphicQuery += YSQLVisitor.asString(leftSelect) + " UNION ALL " + YSQLVisitor.asString(middleSelect)
                + " UNION ALL " + YSQLVisitor.asString(rightSelect);
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

    private List<YSQLExpression> mapped(YSQLAggregate aggregate) {
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

    private List<YSQLExpression> aliasArgs(List<YSQLExpression> originalAggregateArgs) {
        List<YSQLExpression> args = new ArrayList<>();
        int i = 0;
        for (YSQLExpression expr : originalAggregateArgs) {
            args.add(new YSQLAlias(expr, "agg" + i++));
        }
        return args;
    }

    private String getOuterAggregateFunction(YSQLAggregate aggregate) {
        switch (aggregate.getFunction()) {
        case COUNT:
            return YSQLAggregateFunction.SUM + "(agg0)";
        default:
            return aggregate.getFunction().toString() + "(agg0)";
        }
    }

    private YSQLSelect getSelect(List<YSQLExpression> aggregates, List<YSQLExpression> from, YSQLExpression whereClause,
            List<YSQLJoin> joinList) {
        YSQLSelect leftSelect = new YSQLSelect();
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
