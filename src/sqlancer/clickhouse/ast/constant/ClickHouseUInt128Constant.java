package sqlancer.clickhouse.ast.constant;

import java.math.BigInteger;

import com.clickhouse.client.ClickHouseDataType;

public class ClickHouseUInt128Constant extends ClickHouseBiggerIntConstant {

    public ClickHouseUInt128Constant(BigInteger value) {
        super(value);
    }

    @Override
    public ClickHouseDataType getDataType() {
        return ClickHouseDataType.UInt128;
    }
}
