package sqlancer.clickhouse.ast.constant;

import java.math.BigInteger;

import com.clickhouse.client.ClickHouseDataType;

public class ClickHouseUInt64Constant extends ClickHouseBigIntConstant {

    public ClickHouseUInt64Constant(BigInteger value) {
        super(value);
    }

    @Override
    public ClickHouseDataType getDataType() {
        return ClickHouseDataType.UInt64;
    }
}
