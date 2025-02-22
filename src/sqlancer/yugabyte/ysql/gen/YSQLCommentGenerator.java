package sqlancer.yugabyte.ysql.gen;

import sqlancer.common.gen.PnYSQLCommentGeneratorBase;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.yugabyte.ysql.YSQLGlobalState;
import sqlancer.yugabyte.ysql.YSQLSchema.YSQLTable;

/**
 * @see <a href="https://www.postgresql.org/docs/devel/sql-comment.html">COMMENT</a>
 */
public final class YSQLCommentGenerator extends PnYSQLCommentGeneratorBase {

    private YSQLCommentGenerator() {
    }

    public static SQLQueryAdapter generate(YSQLGlobalState globalState) {
        YSQLTable randomTable = globalState.getSchema().getRandomTable();
        String result = generateHelper(globalState, randomTable);

        return new SQLQueryAdapter(result);
    }
}
