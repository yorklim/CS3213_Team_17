package sqlancer.common.oracle;

import sqlancer.ComparatorHelper;
import sqlancer.IgnoreMeException;
import sqlancer.GlobalState;
import sqlancer.SQLGlobalState;

public final class AggregateCheckCommon {

    public static void aggregateCheckCommon(Object state, String firstResult, String secondResult, String originalQuery,
            String metamorphicQuery) {
        String queryFormatString = "-- %s;\n-- result: %s";
        String firstQueryString = String.format(queryFormatString, originalQuery, firstResult);
        String secondQueryString = String.format(queryFormatString, metamorphicQuery, secondResult);

        if (state instanceof GlobalState<?, ?, ?>) {
            ((GlobalState<?, ?, ?>) state).getState().getLocalState()
                    .log(String.format("%s\n%s", firstQueryString, secondQueryString));
        } else if (state instanceof SQLGlobalState<?, ?>) {
            ((SQLGlobalState<?, ?>) state).getState().getLocalState()
                    .log(String.format("%s\n%s", firstQueryString, secondQueryString));
        } else {
            throw new IllegalArgumentException("Unsupported state type: " + state.getClass().getName());
        }

        if (firstResult == null && secondResult != null || firstResult != null && secondResult == null
                || firstResult != null && !firstResult.contentEquals(secondResult)
                        && !ComparatorHelper.isEqualDouble(firstResult, secondResult)) {
            if (secondResult != null && secondResult.contains("Inf")) {
                throw new IgnoreMeException(); // FIXME: average computation
            }
            String assertionMessage = String.format("%s: the results mismatch!\n%s\n%s", firstQueryString,
                    secondQueryString);
            throw new AssertionError(assertionMessage);
        }
    }

}
