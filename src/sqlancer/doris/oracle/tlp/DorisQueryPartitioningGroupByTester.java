package sqlancer.doris.oracle.tlp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import sqlancer.ComparatorHelper;
import sqlancer.Randomly;
import sqlancer.doris.DorisErrors;
import sqlancer.doris.DorisProvider.DorisGlobalState;
import sqlancer.doris.ast.DorisColumnReference;
import sqlancer.doris.ast.DorisExpression;

public class DorisQueryPartitioningGroupByTester extends DorisQueryPartitioningBase {

    public DorisQueryPartitioningGroupByTester(DorisGlobalState state) {
        super(state);
        DorisErrors.addExpressionErrors(errors);
        DorisErrors.addInsertErrors(errors);
    }

    @Override
    public void check() throws SQLException {
        super.check();
        select.setGroupByExpressions(select.getFetchColumns());

        Map<String, String> queryStrings = DorisQueryPartitioningTesterCommon.checkCommon(select, predicate,
                negatedPredicate, isNullPredicate);

        List<String> resultSet = ComparatorHelper
                .getResultSetFirstColumnAsString(queryStrings.get("originalQueryString"), errors, state);
        List<String> combinedString = new ArrayList<>();
        List<String> secondResultSet = ComparatorHelper.getCombinedResultSetNoDuplicates(
                queryStrings.get("firstQueryString"), queryStrings.get("secondQueryString"),
                queryStrings.get("thirdQueryString"), combinedString, true, state, errors);
        ComparatorHelper.assumeResultSetsAreEqual(resultSet, secondResultSet, queryStrings.get("originalQueryString"),
                combinedString, state, ComparatorHelper::canonicalizeResultValue);
    }

    @Override
    List<DorisExpression> generateFetchColumns() {
        return Randomly.nonEmptySubset(targetTables.getColumns()).stream().map(DorisColumnReference::new)
                .collect(Collectors.toList());
    }

}
