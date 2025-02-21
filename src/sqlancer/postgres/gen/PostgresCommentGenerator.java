package sqlancer.postgres.gen;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.gen.PnYSQLCommentGeneratorBase;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.postgres.PostgresGlobalState;
import sqlancer.postgres.PostgresSchema.PostgresTable;

/**
 * @see <a href="https://www.postgresql.org/docs/devel/sql-comment.html">COMMENT</a>
 */
public final class PostgresCommentGenerator extends PnYSQLCommentGeneratorBase {

    private PostgresCommentGenerator() {
    }

    public static SQLQueryAdapter generate(PostgresGlobalState globalState) {
        PostgresTable randomTable = globalState.getSchema().getRandomTable();
        String result = generateHelper(globalState, randomTable);
        return new SQLQueryAdapter(result);
    }

}
