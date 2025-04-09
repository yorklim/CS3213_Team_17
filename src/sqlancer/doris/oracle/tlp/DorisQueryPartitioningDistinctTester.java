package sqlancer.doris.oracle.tlp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sqlancer.ComparatorHelper;
import sqlancer.doris.DorisErrors;
import sqlancer.doris.DorisProvider.DorisGlobalState;

public class DorisQueryPartitioningDistinctTester extends DorisQueryPartitioningBase {

    public DorisQueryPartitioningDistinctTester(DorisGlobalState state) {
        super(state);
        DorisErrors.addExpressionErrors(errors);
        DorisErrors.addInsertErrors(errors);
    }

    @Override
    public void check() throws SQLException {
        super.check();
        select.setDistinct(true);

        Map<String, String> queryStrings = DorisQueryPartitioningTesterCommon.checkCommon(select, predicate,
                negatedPredicate, isNullPredicate);

        List<String> resultSet = ComparatorHelper
                .getResultSetFirstColumnAsString(queryStrings.get("originalQueryString"), errors, state);
        List<String> combinedString = new ArrayList<>();
        String unionString = "SELECT DISTINCT * FROM (" + queryStrings.get("firstQueryString") + " UNION ALL "
                + queryStrings.get("secondQueryString") + " UNION ALL " + queryStrings.get("thirdQueryString")
                + ") tmpTable";
        combinedString.add(unionString);
        List<String> secondResultSet = ComparatorHelper.getResultSetFirstColumnAsString(unionString, errors, state);
        ComparatorHelper.assumeResultSetsAreEqual(resultSet, secondResultSet, queryStrings.get("originalQueryString"),
                combinedString, state, ComparatorHelper::canonicalizeResultValue);
    }

}
