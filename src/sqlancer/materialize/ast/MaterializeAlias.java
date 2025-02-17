package sqlancer.materialize.ast;

import sqlancer.common.ast.DBAlias;

public class MaterializeAlias extends DBAlias<MaterializeExpression> implements MaterializeExpression {

    private final MaterializeExpression expr;

    public MaterializeAlias(MaterializeExpression expr, String alias) {
        super(alias);
        this.expr = expr;
    }

    @Override
    public MaterializeExpression getExpression() {
        return expr;
    }
}
