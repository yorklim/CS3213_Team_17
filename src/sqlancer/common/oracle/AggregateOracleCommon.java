package sqlancer.common.oracle;

import java.util.Map;

import sqlancer.ComparatorHelper;
import sqlancer.GlobalState;
import sqlancer.IgnoreMeException;
import sqlancer.SQLGlobalState;

public final class AggregateOracleCommon {

    private AggregateOracleCommon() {
    }

    public static void aggregateCheckCommon(Object state, Map<String, String> queryResults) {
        String queryFormatString = "-- %s;\n-- result: %s";
        String firstQueryString = String.format(queryFormatString, queryResults.get("originalQuery"),
                queryResults.get("firstResult"));
        String secondQueryString = String.format(queryFormatString, queryResults.get("metamorphicQuery"),
                queryResults.get("secondResult"));

        if (state instanceof GlobalState<?, ?, ?>) {
            ((GlobalState<?, ?, ?>) state).getState().getLocalState()
                    .log(String.format("%s\n%s", firstQueryString, secondQueryString));
        } else if (state instanceof SQLGlobalState<?, ?>) {
            ((SQLGlobalState<?, ?>) state).getState().getLocalState()
                    .log(String.format("%s\n%s", firstQueryString, secondQueryString));
        } else {
            throw new IllegalArgumentException("Unsupported state type: " + state.getClass().getName());
        }

        if (queryResults.get("firstResult") == null && queryResults.get("secondResult") != null
                || queryResults.get("firstResult") != null && queryResults.get("secondResult") == null
                || queryResults.get("firstResult") != null
                        && !queryResults.get("firstResult").contentEquals(queryResults.get("secondResult"))
                        && !ComparatorHelper.isEqualDouble(queryResults.get("firstResult"),
                                queryResults.get("secondResult"))) {
            if (queryResults.get("secondResult") != null && queryResults.get("secondResult").contains("Inf")) {
                throw new IgnoreMeException(); // FIXME: average computation
            }
            String assertionMessage = String.format("%s: the results mismatch!\n%s\n%s", firstQueryString,
                    secondQueryString);
            throw new AssertionError(assertionMessage);
        }
    }

}
