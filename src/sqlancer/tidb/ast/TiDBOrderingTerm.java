package sqlancer.tidb.ast;

import sqlancer.common.ast.OrderingTerm;

public class TiDBOrderingTerm extends OrderingTerm<TiDBExpression> implements TiDBExpression {

    private final TiDBExpression expr;

    public TiDBOrderingTerm(TiDBExpression expr, boolean asc) {
        super(asc);
        this.expr = expr;

    }

    @Override
    public TiDBExpression getExpression() {
        return expr;
    }
}
