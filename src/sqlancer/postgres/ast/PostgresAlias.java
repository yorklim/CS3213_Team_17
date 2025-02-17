package sqlancer.postgres.ast;

import sqlancer.common.ast.DBAlias;

public class PostgresAlias extends DBAlias<PostgresExpression> implements PostgresExpression {

    private final PostgresExpression expr;

    public PostgresAlias(PostgresExpression expr, String alias) {
        super(alias);
        this.expr = expr;
    }

    @Override
    public PostgresExpression getExpression() {
        return expr;
    }
}
