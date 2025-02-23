package sqlancer;

import java.util.ArrayList;
import java.util.List;

import sqlancer.common.query.Query;

public class StatementExecutor<G extends GlobalState<?, ?, ?>, A extends AbstractAction<G>> {

    private final G globalState;
    private final A[] actions;
    private final ActionMapper<G, A> mapping;
    private final AfterQueryAction queryConsumer;

    @FunctionalInterface
    public interface AfterQueryAction {
        void notify(Query<?> q) throws Exception;
    }

    @FunctionalInterface
    public interface ActionMapper<T, A> {
        int map(T globalState, A action);
    }

    public StatementExecutor(G globalState, A[] actions, ActionMapper<G, A> mapping, AfterQueryAction queryConsumer) {
        this.globalState = globalState;
        this.actions = actions.clone();
        this.mapping = mapping;
        this.queryConsumer = queryConsumer;
    }

    @SuppressWarnings("unchecked")
    public void executeStatements() throws Exception {
        Randomly r = globalState.getRandomly();
        int[] nrRemaining = new int[actions.length];
        List<A> availableActions = new ArrayList<>();
        int total = 0;
        for (int i = 0; i < actions.length; i++) {
            A action = actions[i];
            // Randomly chooses the number of times the action will be performed
            int nrPerformed = mapping.map(globalState, action);
            if (nrPerformed != 0) {
                availableActions.add(action);
            }
            nrRemaining[i] = nrPerformed;
            total += nrPerformed;
        }
        while (total != 0) {
            A nextAction = null;
            int selection = r.getInteger(0, total);
            int previousRange = 0;
            int i;
            // Does a random action from the available actions
            for (i = 0; i < nrRemaining.length; i++) {
                if (previousRange <= selection && selection < previousRange + nrRemaining[i]) {
                    nextAction = actions[i];
                    break;
                } else {
                    previousRange += nrRemaining[i];
                }
            }
            assert nextAction != null;
            assert nrRemaining[i] > 0;
            nrRemaining[i]--;
            @SuppressWarnings("rawtypes")
            Query query = null;
            try {
                boolean success;
                int nrTries = 0;
                do {
                    // Gets the query from the action, using gen files in specific databases
                    query = nextAction.getQuery(globalState);
                    // Executes the query
                    success = globalState.executeStatement(query);
                    // Tries querying again if the action can be retried and the query was not successful
                } while (nextAction.canBeRetried() && !success
                        && nrTries++ < globalState.getOptions().getNrStatementRetryCount());
            } catch (IgnoreMeException ignored) {

            }
            // Updates Global state if the query affects the schema
            if (query != null && query.couldAffectSchema()) {
                globalState.updateSchema();
                // I do not think this does anything
                queryConsumer.notify(query);
            }
            total--;
        }
    }
}
