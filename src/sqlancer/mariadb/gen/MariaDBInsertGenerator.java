package sqlancer.mariadb.gen;

import sqlancer.Randomly;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.common.query.SQLQueryAdapter;
import sqlancer.mariadb.MariaDBErrors;
import sqlancer.mariadb.MariaDBProvider.MariaDBGlobalState;
import sqlancer.mariadb.MariaDBSchema.MariaDBTable;
import sqlancer.mariadb.ast.MariaDBVisitor;

public final class MariaDBInsertGenerator {

    private MariaDBInsertGenerator() {
    }

    public static SQLQueryAdapter insert(MariaDBGlobalState globalState) {
        MariaDBTable randomTable = globalState.getSchema().getRandomTable();
        StringBuilder sb = new StringBuilder();
        Randomly r = globalState.getRandomly();
        sb.append("INSERT INTO ");
        sb.append(randomTable.getName());
        sb.append(" VALUES (");
        for (int i = 0; i < randomTable.getColumns().size(); i++) {
            if (i != 0) {
                sb.append(", ");
            }
            if (Randomly.getBooleanWithSmallProbability()) {
                sb.append(MariaDBVisitor.asString(MariaDBExpressionGenerator.getRandomConstant(r)));
            } else {
                sb.append(MariaDBVisitor.asString(
                        MariaDBExpressionGenerator.getRandomConstant(r, randomTable.getColumns().get(i).getType())));
            }
        }
        sb.append(")");
        ExpectedErrors errors = new ExpectedErrors();
        MariaDBErrors.addInsertErrors(errors);
        return new SQLQueryAdapter(sb.toString(), errors);
    }

}
