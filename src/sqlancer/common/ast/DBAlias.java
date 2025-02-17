package sqlancer.common.ast;

import sqlancer.common.visitor.UnaryOperation;

public abstract class DBAlias<T> implements UnaryOperation<T> {

    private final String alias;

    public DBAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String getOperatorRepresentation() {
        return " as " + alias;
    }

    @Override
    public OperatorKind getOperatorKind() {
        return OperatorKind.POSTFIX;
    }

    @Override
    public boolean omitBracketsWhenPrinting() {
        return true;
    }

}
