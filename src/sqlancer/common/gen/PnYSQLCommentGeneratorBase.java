package sqlancer.common.gen;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.SQLGlobalState;

public abstract class PnYSQLCommentGeneratorBase {
    protected enum Action {
        INDEX, COLUMN, STATISTICS, TABLE
    }

    protected static <T extends PnYSQLTable<?, ?, ?>> String generateHelper(SQLGlobalState<?, ?> globalState,
            T randomTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("COMMENT ON ");
        Action type = Randomly.fromOptions(Action.values());
        switch (type) {
        case INDEX:
            sb.append("INDEX ");
            if (randomTable.getIndexes().isEmpty()) {
                throw new IgnoreMeException();
            } else {
                sb.append(randomTable.getRandomIndex().getIndexName());
            }
            break;
        case COLUMN:
            sb.append("COLUMN ");
            sb.append(randomTable.getRandomColumn().getFullQualifiedName());
            break;
        case STATISTICS:
            sb.append("STATISTICS ");
            if (randomTable.getStatistics().isEmpty()) {
                throw new IgnoreMeException();
            } else {
                sb.append(randomTable.getStatistics().get(0).getName());
            }
            break;
        case TABLE:
            sb.append("TABLE ");
            if (randomTable.isView()) {
                throw new IgnoreMeException();
            }
            sb.append(randomTable.getName());
            break;
        default:
            throw new AssertionError(type);
        }
        sb.append(" IS ");
        if (Randomly.getBoolean()) {
            sb.append("NULL");
        } else {
            sb.append("'");
            sb.append(globalState.getRandomly().getString().replace("'", "''"));
            sb.append("'");
        }
        return sb.toString();
    }
}
