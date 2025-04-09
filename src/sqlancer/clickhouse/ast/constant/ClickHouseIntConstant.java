package sqlancer.clickhouse.ast.constant;

import java.math.BigInteger;

import com.clickhouse.client.ClickHouseDataType;

import sqlancer.IgnoreMeException;
import sqlancer.clickhouse.ast.ClickHouseConstant;

public abstract class ClickHouseIntConstant extends ClickHouseConstant {

    private final Object value;

    public ClickHouseIntConstant(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public ClickHouseConstant applyLess(ClickHouseConstant right) {
        if (this.getDataType() == right.getDataType()) {
            return this.asInt() < right.asInt() ? ClickHouseCreateConstant.createTrue()
                    : ClickHouseCreateConstant.createFalse();
        }
        throw new IgnoreMeException();
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public long asInt() {
        return (int) value;
    }

    @Override
    public boolean asBooleanNotNull() {
        return (int) value != 0;
    }

    @Override
    public boolean compareInternal(Object val) {
        return (int) value == (int) val;
    }

    @Override
    public ClickHouseConstant cast(ClickHouseDataType type) {
        int val = (int) value;
        switch (type) {
        case String:
            return ClickHouseCreateConstant.createStringConstant(this.toString());
        case UInt8:
            return ClickHouseCreateConstant.createUInt8Constant(val);
        case Int8:
            return ClickHouseCreateConstant.createInt8Constant(val);
        case UInt16:
            return ClickHouseCreateConstant.createUInt16Constant(val);
        case Int16:
            return ClickHouseCreateConstant.createInt16Constant(val);
        case UInt32:
            return ClickHouseCreateConstant.createUInt32Constant(val);
        case Int32:
            return ClickHouseCreateConstant.createInt32Constant(val);
        case UInt64:
            return ClickHouseCreateConstant.createUInt64Constant(BigInteger.valueOf(val));
        case Int64:
            return ClickHouseCreateConstant.createInt64Constant(BigInteger.valueOf(val));
        case Float32:
            return ClickHouseCreateConstant.createFloat32Constant((float) val);
        case Float64:
            return ClickHouseCreateConstant.createFloat64Constant(val);
        case Nothing:
            return ClickHouseCreateConstant.createNullConstant();
        case Bool:
            return ClickHouseCreateConstant.createBooleanConstant(val != 0);
        case IntervalYear:
        case IntervalQuarter:
        case IntervalMonth:
        case IntervalWeek:
        case IntervalDay:
        case IntervalHour:
        case IntervalMinute:
        case IntervalSecond:
        case Date:
        case Date32:
        case DateTime:
        case DateTime32:
        case DateTime64:
        case Decimal:
        case Decimal32:
        case Decimal64:
        case Decimal128:
        case Decimal256:
        case UUID:
        case Enum:
        case Enum8:
        case Enum16:
        case IPv4:
        case IPv6:
        case FixedString:
        case AggregateFunction:
        case SimpleAggregateFunction:
        case Array:
        case Map:
        case Nested:
        case Tuple:
        case Point:
        case Polygon:
        case MultiPolygon:
        case Ring:
        default:
            throw new AssertionError(type);
        }
    }
}
