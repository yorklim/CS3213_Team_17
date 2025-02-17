package sqlancer.clickhouse.ast;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Optional;

import com.clickhouse.client.ClickHouseDataType;

import sqlancer.clickhouse.ast.constant.ClickHouseCreateConstant;

import sqlancer.common.ast.AstUtils;

public final class ClickHouseCast extends ClickHouseExpression {

    static Connection castDatabase;

    private ClickHouseCast() {
    }

    public static Optional<Boolean> isTrue(ClickHouseConstant value) {
        ClickHouseConstant numericValue;
        if (value.getDataType() == ClickHouseDataType.Nothing) {
            return Optional.empty();
        }
        if (value.getDataType() == ClickHouseDataType.String) {
            numericValue = castToNumeric(value);
        } else {
            numericValue = value;
        }
        assert numericValue.getDataType() != ClickHouseDataType.String : numericValue + "should have been converted";
        switch (numericValue.getDataType()) {
        case Int32:
            return Optional.of(numericValue.asInt() != 0);
        case Float64:
            double doubleVal = numericValue.asDouble();
            return Optional.of(doubleVal != 0 && !Double.isNaN(doubleVal));
        default:
            throw new AssertionError(numericValue);
        }
    }

    // SELECT CAST('-1.370998801E9' AS INTEGER) == -1
    public static ClickHouseConstant castToInt(ClickHouseConstant cons) {
        switch (cons.getDataType()) {
        case Nothing:
            return ClickHouseCreateConstant.createNullConstant();
        case Int32:
            return cons;
        case Float64:
            return ClickHouseCreateConstant.createInt32Constant((long) cons.asDouble());
        case String:
            String asString = cons.asString();
            while (AstUtils.startsWithWhitespace(asString)) {
                asString = asString.substring(1);
            }
            if (!asString.isEmpty() && AstUtils.unprintAbleCharThatLetsBecomeNumberZero(asString)) {
                return ClickHouseCreateConstant.createInt32Constant(0);
            }
            Long result = AstUtils.castToIntStringHelper(asString);
            if (result == null) {
                return ClickHouseCreateConstant.createInt32Constant(0);
            } else {
                return ClickHouseCreateConstant.createInt32Constant(result);
            }
        default:
            throw new AssertionError();
        }

    }

    public static ClickHouseConstant castToReal(ClickHouseConstant cons) {
        ClickHouseConstant numericValue = castToNumeric(cons);
        if (numericValue.getDataType() == ClickHouseDataType.Int32) {
            return ClickHouseCreateConstant.createFloat64Constant(numericValue.asInt());
        } else {
            return numericValue;
        }
    }

    public static ClickHouseConstant castToNumericNoNumAsRealZero(ClickHouseConstant value) {
        return convertInternal(value, false, true, true);
    }

    public static ClickHouseConstant castToNumericFromNumOperand(ClickHouseConstant value) {
        return convertInternal(value, false, false, false);
    }

    /*
     * Applies numeric affinity to a value.
     */
    public static ClickHouseConstant castToNumeric(ClickHouseConstant value) {
        return convertInternal(value, true, false, false);
    }

    private static ClickHouseConstant convertInternal(ClickHouseConstant value, boolean convertRealToInt,
            boolean noNumIsRealZero, boolean convertIntToReal) throws AssertionError {
        switch (value.getDataType()) {
        case Nothing:
            return ClickHouseCreateConstant.createNullConstant();
        case Int32:
        case Float64:
            return value;
        case String:
            String asString = value.asString();
            while (AstUtils.startsWithWhitespace(asString)) {
                asString = asString.substring(1);
            }
            if (!asString.isEmpty() && AstUtils.unprintAbleCharThatLetsBecomeNumberZero(asString)) {
                return ClickHouseCreateConstant.createInt32Constant(0);
            }
            if (asString.toLowerCase().startsWith("-infinity") || asString.toLowerCase().startsWith("infinity")
                    || asString.startsWith("NaN")) {
                return ClickHouseCreateConstant.createInt32Constant(0);
            }

            ClickHouseConstant result = AstUtils.convertInternalHelper(asString, convertRealToInt, convertIntToReal,
                x -> ClickHouseCreateConstant.createInt32Constant(x), x -> ClickHouseCreateConstant.createFloat64Constant(x));

            if (result != null) {
                return result;
            }

            if (noNumIsRealZero) {
                return ClickHouseCreateConstant.createFloat64Constant(0.0);
            } else {
                return ClickHouseCreateConstant.createInt32Constant(0);
            }
        default:
            throw new AssertionError(value);
        }
    }

    

    public static ClickHouseConstant castToText(ClickHouseConstant cons) {
        if (cons.getDataType() == ClickHouseDataType.String) {
            return cons;
        }
        if (cons.getDataType() == ClickHouseDataType.Nothing) {
            return cons;
        }
        if (cons.getDataType() == ClickHouseDataType.Float64) {
            if (cons.asDouble() == Double.POSITIVE_INFINITY) {
                return ClickHouseCreateConstant.createStringConstant("Inf");
            } else if (cons.asDouble() == Double.NEGATIVE_INFINITY) {
                return ClickHouseCreateConstant.createStringConstant("-Inf");
            }
            return castRealToText(cons);
        }
        if (cons.getDataType() == ClickHouseDataType.Int32) {
            return ClickHouseCreateConstant.createStringConstant(String.valueOf(cons.asInt()));
        }
        return null;
    }

    private static synchronized ClickHouseConstant castRealToText(ClickHouseConstant cons) throws AssertionError {
        try (Statement s = castDatabase.createStatement()) {
            String castResult = s.executeQuery("SELECT CAST(" + cons.asDouble() + " AS TEXT)").getString(1);
            return ClickHouseCreateConstant.createStringConstant(castResult);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static ClickHouseConstant asBoolean(ClickHouseConstant val) {
        Optional<Boolean> boolVal = isTrue(val);
        if (boolVal.isPresent()) {
            return ClickHouseCreateConstant.createBoolean(boolVal.get());
        } else {
            return ClickHouseCreateConstant.createNullConstant();
        }
    }

}
