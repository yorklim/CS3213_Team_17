package sqlancer.clickhouse.ast.constant;

import java.math.BigInteger;

import com.clickhouse.client.ClickHouseDataType;

import sqlancer.clickhouse.ast.ClickHouseConstant;

public abstract class ClickHouseBigIntConstant extends ClickHouseIntConstant {

    public ClickHouseBigIntConstant(BigInteger value) {
        super(value);
    }

    @Override
    public boolean asBooleanNotNull() {
        return (BigInteger) this.getValue() != BigInteger.ZERO;
    }

    @Override
    public boolean compareInternal(Object val) {
        return ((BigInteger) this.getValue()).compareTo((BigInteger) val) == 0;
    }

    @Override
    public long asInt() {
        return ((BigInteger) this.getValue()).longValueExact();
    }

    @Override
    public ClickHouseConstant cast(ClickHouseDataType type) {
        long val = this.asInt();
        BigInteger value = (BigInteger) this.getValue();
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
            return ClickHouseCreateConstant.createUInt64Constant(value);
        case Int64:
            return ClickHouseCreateConstant.createInt64Constant(value);
        case Float32:
            return ClickHouseCreateConstant.createFloat32Constant(value.floatValue());
        case Float64:
            return ClickHouseCreateConstant.createFloat64Constant(value.doubleValue());
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
