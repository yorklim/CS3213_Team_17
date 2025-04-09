package sqlancer.clickhouse.ast.constant;

import java.math.BigInteger;

import com.clickhouse.client.ClickHouseDataType;

public class ClickHouseInt64Constant extends ClickHouseBigIntConstant {

    public ClickHouseInt64Constant(BigInteger value) {
        super(value);
    }

    @Override
    public ClickHouseDataType getDataType() {
        return ClickHouseDataType.Int64;
    }
}
