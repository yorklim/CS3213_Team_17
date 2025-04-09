package sqlancer.common.ast;

import sqlancer.common.visitor.UnaryOperation;

public abstract class OrderingTerm<T> implements UnaryOperation<T> {

    private final boolean asc;

    public OrderingTerm(boolean asc) {
        this.asc = asc;
    }

    @Override
    public String getOperatorRepresentation() {
        return asc ? "ASC" : "DESC";
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
