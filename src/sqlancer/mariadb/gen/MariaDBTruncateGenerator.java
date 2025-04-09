package sqlancer.mariadb.gen;

import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.mariadb.MariaDBErrors;
import sqlancer.mariadb.MariaDBProvider.MariaDBGlobalState;

public final class MariaDBTruncateGenerator {

    private MariaDBTruncateGenerator() {
    }

    public static SQLQueryAdapter truncate(MariaDBGlobalState globalState) {
        StringBuilder sb = new StringBuilder("TRUNCATE ");
        sb.append(globalState.getSchema().getRandomTable().getName());
        sb.append(" ");
        MariaDBCommon.addWaitClause(sb);
        ExpectedErrors errors = new ExpectedErrors();
        MariaDBErrors.addCommonErrors(errors);
        return new SQLQueryAdapter(sb.toString(), errors);
    }
}
