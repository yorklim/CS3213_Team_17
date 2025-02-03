package sqlancer.common.oracle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sqlancer.ComparatorHelper;
import sqlancer.Randomly;
import sqlancer.Reproducer;
import sqlancer.SQLGlobalState;
import sqlancer.common.ast.newast.Expression;
import sqlancer.common.ast.newast.Join;
import sqlancer.common.ast.newast.Select;
import sqlancer.common.gen.TLPWhereGenerator;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.schema.AbstractSchema;
import sqlancer.common.schema.AbstractTable;
import sqlancer.common.schema.AbstractTableColumn;
import sqlancer.common.schema.AbstractTables;

public class TLPWhereOracle<Z extends Select<J, E, T, C>, J extends Join<E, T, C>, E extends Expression<C>, S extends AbstractSchema<?, T>, T extends AbstractTable<C, ?, ?>, C extends AbstractTableColumn<?, ?>, G extends SQLGlobalState<?, S>>
        implements TestOracle<G> {

    private final G state;

    private TLPWhereGenerator<Z, J, E, T, C> gen;
    private final ExpectedErrors errors;

    private Reproducer<G> reproducer;
    private String generatedQueryString;

    private class TLPWhereReproducer implements Reproducer<G> {
        final String firstQueryString;
        final String secondQueryString;
        final String thirdQueryString;
        final String originalQueryString;
        final List<String> resultSet;
        final boolean orderBy;

        TLPWhereReproducer(String firstQueryString, String secondQueryString, String thirdQueryString,
                String originalQueryString, List<String> resultSet, boolean orderBy) {
            this.firstQueryString = firstQueryString;
            this.secondQueryString = secondQueryString;
            this.thirdQueryString = thirdQueryString;
            this.originalQueryString = originalQueryString;
            this.resultSet = resultSet;
            this.orderBy = orderBy;
        }

        @Override
        public boolean bugStillTriggers(G globalState) {
            try {
                List<String> combinedString1 = new ArrayList<>();
                List<String> secondResultSet1 = ComparatorHelper.getCombinedResultSet(firstQueryString,
                        secondQueryString, thirdQueryString, combinedString1, !orderBy, globalState, errors);
                ComparatorHelper.assumeResultSetsAreEqual(resultSet, secondResultSet1, originalQueryString,
                        combinedString1, globalState);
            } catch (AssertionError triggeredError) {
                return true;
            } catch (SQLException ignored) {
            }
            return false;
        }
    }

    public TLPWhereOracle(G state, TLPWhereGenerator<Z, J, E, T, C> gen, ExpectedErrors expectedErrors) {
        if (state == null || gen == null || expectedErrors == null) {
            throw new IllegalArgumentException("Null variables used to initialize test oracle.");
        }
        this.state = state;
        this.gen = gen;
        this.errors = expectedErrors;
    }

    @Override
    public void check() throws SQLException {
        reproducer = null;
        S s = state.getSchema();
        AbstractTables<T, C> targetTables = TestOracleUtils.getRandomTableNonEmptyTables(s);
        gen = gen.setTablesAndColumns(targetTables);

        Select<J, E, T, C> select = gen.generateSelect();

        boolean shouldCreateDummy = true;
        select.setFetchColumns(gen.generateFetchColumns(shouldCreateDummy));
        select.setJoinClauses(gen.getRandomJoinClauses());
        select.setFromList(gen.getTableRefs());
        select.setWhereClause(null);

        String originalQueryString = select.asString();
        generatedQueryString = originalQueryString;
        List<String> firstResultSet = ComparatorHelper.getResultSetFirstColumnAsString(originalQueryString, errors,
                state);

        boolean orderBy = Randomly.getBooleanWithSmallProbability();
        if (orderBy) {
            select.setOrderByClauses(gen.generateOrderBys());
        }

        TestOracleUtils.PredicateVariants<E, C> predicates = TestOracleUtils.initializeTernaryPredicateVariants(gen,
                gen.generateBooleanExpression());
        select.setWhereClause(predicates.predicate);
        String firstQueryString = select.asString();
        select.setWhereClause(predicates.negatedPredicate);
        String secondQueryString = select.asString();
        select.setWhereClause(predicates.isNullPredicate);
        String thirdQueryString = select.asString();

        List<String> combinedString = new ArrayList<>();
        List<String> secondResultSet = ComparatorHelper.getCombinedResultSet(firstQueryString, secondQueryString,
                thirdQueryString, combinedString, !orderBy, state, errors);

        ComparatorHelper.assumeResultSetsAreEqual(firstResultSet, secondResultSet, originalQueryString, combinedString,
                state);

        reproducer = new TLPWhereReproducer(firstQueryString, secondQueryString, thirdQueryString, originalQueryString,
                firstResultSet, orderBy);
    }

    @Override
    public Reproducer<G> getLastReproducer() {
        return reproducer;
    }

    @Override
    public String getLastQueryString() {
        return generatedQueryString;
    }
}
