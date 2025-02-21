package sqlancer.cnosdb.ast;

import sqlancer.common.ast.DBAlias;

public class CnosDBAlias extends DBAlias<CnosDBExpression> implements CnosDBExpression {

    private final CnosDBExpression expr;

    public CnosDBAlias(CnosDBExpression expr, String alias) {
        super(alias);
        this.expr = expr;
    }

    @Override
    public CnosDBExpression getExpression() {
        return expr;
    }
}
