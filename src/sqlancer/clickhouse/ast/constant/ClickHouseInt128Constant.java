package sqlancer.clickhouse.ast.constant;

import java.math.BigInteger;

import com.clickhouse.client.ClickHouseDataType;

public class ClickHouseInt128Constant extends ClickHouseBiggerIntConstant {

    public ClickHouseInt128Constant(BigInteger value) {
        super(value);
    }

    @Override
    public ClickHouseDataType getDataType() {
        return ClickHouseDataType.Int128;
    }
}
