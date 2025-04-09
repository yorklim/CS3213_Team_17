package sqlancer.cockroachdb.ast;

import sqlancer.common.ast.OrderingTerm;

public class CockroachDBOrderingTerm extends OrderingTerm<CockroachDBExpression> implements CockroachDBExpression {

    private final CockroachDBExpression expr;

    public CockroachDBOrderingTerm(CockroachDBExpression expr, boolean asc) {
        super(asc);
        this.expr = expr;
    }

    @Override
    public CockroachDBExpression getExpression() {
        return expr;
    }
}
