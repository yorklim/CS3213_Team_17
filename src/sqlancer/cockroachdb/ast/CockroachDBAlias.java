package sqlancer.cockroachdb.ast;

import sqlancer.common.ast.DBAlias;

public class CockroachDBAlias extends DBAlias<CockroachDBExpression> implements CockroachDBExpression {

    private final CockroachDBExpression expr;

    public CockroachDBAlias(CockroachDBExpression expr, String alias) {
        super(alias);
        this.expr = expr;
    }

    @Override
    public CockroachDBExpression getExpression() {
        return expr;
    }
}
