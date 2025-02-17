package sqlancer.clickhouse.ast;

import sqlancer.common.visitor.BinaryOperation;

public abstract class ClickHouseBinaryExpression extends ClickHouseExpression
        implements BinaryOperation<ClickHouseExpression> {
            
    private final ClickHouseExpression left;
    private final ClickHouseExpression right;

    public ClickHouseBinaryExpression(ClickHouseExpression left, ClickHouseExpression right) {
            this.left = left;
            this.right = right;
    }

    @Override
    public final ClickHouseExpression getLeft() {
        return this.left;
    }

    @Override
    public final ClickHouseExpression getRight() {
        return this.right;
    }
}
