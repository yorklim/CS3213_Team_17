package sqlancer.clickhouse.ast;

import sqlancer.Randomly;

public class ClickHouseBinaryArithmeticOperation extends ClickHouseBinaryExpression {

    public enum ClickHouseBinaryArithmeticOperator {
        ADD("+"), //
        MINUS("-"), //
        MULT("*"), //
        DIV("/"), //
        MODULO("%"); //

        String textRepresentation;

        ClickHouseBinaryArithmeticOperator(String textRepresentation) {
            this.textRepresentation = textRepresentation;
        }

        public static ClickHouseBinaryArithmeticOperator getRandom() {
            return Randomly.fromOptions(values());
        }

        public String getTextRepresentation() {
            return textRepresentation;
        }
    }

    private final ClickHouseBinaryArithmeticOperation.ClickHouseBinaryArithmeticOperator operation;

    public ClickHouseBinaryArithmeticOperation(ClickHouseExpression left, ClickHouseExpression right,
            ClickHouseBinaryArithmeticOperation.ClickHouseBinaryArithmeticOperator operation) {
        super(left, right);
        this.operation = operation;
    }

    public ClickHouseBinaryArithmeticOperation.ClickHouseBinaryArithmeticOperator getOperator() {
        return operation;
    }

    @Override
    public String getOperatorRepresentation() {
        return operation.getTextRepresentation();
    }

    public static ClickHouseBinaryArithmeticOperation create(ClickHouseExpression left, ClickHouseExpression right,
            ClickHouseBinaryArithmeticOperation.ClickHouseBinaryArithmeticOperator op) {
        return new ClickHouseBinaryArithmeticOperation(left, right, op);
    }
}
