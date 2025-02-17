package sqlancer.sqlite3.ast;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.Optional;

import sqlancer.IgnoreMeException;
import sqlancer.sqlite3.schema.SQLite3DataType;

import sqlancer.common.ast.AstUtils;

public final class SQLite3Cast {

    public static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");

    static Connection castDatabase;

    private SQLite3Cast() {
    }

    public static Optional<Boolean> isTrue(SQLite3Constant value) {
        SQLite3Constant numericValue;
        if (value.getDataType() == SQLite3DataType.NULL) {
            return Optional.empty();
        }
        if (value.getDataType() == SQLite3DataType.TEXT || value.getDataType() == SQLite3DataType.BINARY) {
            numericValue = castToNumeric(value);
        } else {
            numericValue = value;
        }
        assert numericValue.getDataType() != SQLite3DataType.TEXT : numericValue + "should have been converted";
        switch (numericValue.getDataType()) {
        case INT:
            return Optional.of(numericValue.asInt() != 0);
        case REAL:
            double doubleVal = numericValue.asDouble();
            return Optional.of(doubleVal != 0 && !Double.isNaN(doubleVal));
        default:
            throw new AssertionError(numericValue);
        }
    }

    public static void checkDoubleIsInsideDangerousRange(double doubleVal) {
        // high double-values might result in small rounding differences between Java and SQLite
        if (Math.abs(doubleVal) > 1e15) {
            throw new IgnoreMeException();
        }
    }

    // SELECT CAST('-1.370998801E9' AS INTEGER) == -1
    public static SQLite3Constant castToInt(SQLite3Constant originalCons) {
        SQLite3Constant cons = originalCons;
        if (cons.getDataType() == SQLite3DataType.BINARY) {
            String text = new String(cons.asBinary(), DEFAULT_ENCODING);
            cons = SQLite3Constant.createTextConstant(text);
        }
        switch (cons.getDataType()) {
        case NULL:
            return SQLite3Constant.createNullConstant();
        case INT:
            return cons;
        case REAL:
            checkDoubleIsInsideDangerousRange(cons.asDouble());
            return SQLite3Constant.createIntConstant((long) cons.asDouble());
        case TEXT:
            String asString = cons.asString();
            while (AstUtils.startsWithWhitespace(asString)) {
                asString = asString.substring(1);
            }
            if (!asString.isEmpty() && AstUtils.unprintAbleCharThatLetsBecomeNumberZero(asString)) {
                return SQLite3Constant.createIntConstant(0);
            }

            Long result = AstUtils.castToIntStringHelper(asString);
            if (result == null) {
                return SQLite3Constant.createIntConstant(0);
            } else {
                return SQLite3Constant.createIntConstant(result);
            }
        default:
            throw new AssertionError();
        }

    }

    public static SQLite3Constant castToReal(SQLite3Constant cons) {
        SQLite3Constant numericValue = castToNumeric(cons);
        if (numericValue.getDataType() == SQLite3DataType.INT) {
            double val = numericValue.asInt();
            checkDoubleIsInsideDangerousRange(val);
            return SQLite3Constant.createRealConstant(val);
        } else {
            return numericValue;
        }
    }

    public static SQLite3Constant castToNumericNoNumAsRealZero(SQLite3Constant value) {
        return convertInternal(value, false, true, true);
    }

    public static SQLite3Constant castToNumericFromNumOperand(SQLite3Constant value) {
        return convertInternal(value, false, false, false);
    }

    /*
     * Applies numeric affinity to a value.
     */
    public static SQLite3Constant castToNumeric(SQLite3Constant value) {
        return convertInternal(value, true, false, false);
    }

    private static SQLite3Constant convertInternal(SQLite3Constant originalValue, boolean convertRealToInt,
            boolean noNumIsRealZero, boolean convertIntToReal) throws AssertionError {
        SQLite3Constant value = originalValue;
        if (value.getDataType() == SQLite3DataType.BINARY) {
            String text = new String(value.asBinary(), DEFAULT_ENCODING);
            value = SQLite3Constant.createTextConstant(text);
        }
        switch (value.getDataType()) {
        case NULL:
            return SQLite3Constant.createNullConstant();
        case INT:
        case REAL:
            return value;
        case TEXT:
            String asString = value.asString();
            while (AstUtils.startsWithWhitespace(asString)) {
                asString = asString.substring(1);
            }
            if (!asString.isEmpty() && AstUtils.unprintAbleCharThatLetsBecomeNumberZero(asString)) {
                return SQLite3Constant.createIntConstant(0);
            }
            if (asString.toLowerCase().startsWith("-infinity") || asString.toLowerCase().startsWith("infinity")
                    || asString.startsWith("NaN")) {
                return SQLite3Constant.createIntConstant(0);
            }

            SQLite3Constant result = AstUtils.convertInternalHelper(asString, convertRealToInt, convertIntToReal,
                x -> SQLite3Constant.createIntConstant(x), x -> SQLite3Constant.createRealConstant(x));
            if (result != null) {
                return result;
            }

            if (noNumIsRealZero) {
                return SQLite3Constant.createRealConstant(0.0);
            } else {
                return SQLite3Constant.createIntConstant(0);
            }
        default:
            throw new AssertionError(value);
        }
    }

    public static SQLite3Constant castToText(SQLite3Constant cons) {
        if (cons.getDataType() == SQLite3DataType.TEXT) {
            return cons;
        }
        if (cons.getDataType() == SQLite3DataType.NULL) {
            return cons;
        }
        if (cons.getDataType() == SQLite3DataType.REAL) {
            if (cons.asDouble() == Double.POSITIVE_INFINITY) {
                return SQLite3Constant.createTextConstant("Inf");
            } else if (cons.asDouble() == Double.NEGATIVE_INFINITY) {
                return SQLite3Constant.createTextConstant("-Inf");
            } else {
                return null;
            }
        }
        if (cons.getDataType() == SQLite3DataType.INT) {
            return SQLite3Constant.createTextConstant(String.valueOf(cons.asInt()));
        }
        return null;
    }

    public static SQLite3Constant asBoolean(SQLite3Constant val) {
        Optional<Boolean> boolVal = isTrue(val);
        if (boolVal.isPresent()) {
            return SQLite3Constant.createBoolean(boolVal.get());
        } else {
            return SQLite3Constant.createNullConstant();
        }
    }

    public static SQLite3Constant castToBlob(SQLite3Constant cons) {
        if (cons.isNull()) {
            return cons;
        } else {
            SQLite3Constant stringVal = SQLite3Cast.castToText(cons);
            if (stringVal == null) {
                return null;
            } else {
                return SQLite3Constant.createBinaryConstant(stringVal.asString().getBytes(DEFAULT_ENCODING));
            }
        }
    }

}
