package sqlancer.clickhouse.ast.constant;

import java.math.BigInteger;

import com.clickhouse.client.ClickHouseDataType;

public class ClickHouseInt256Constant extends ClickHouseBiggerIntConstant {

    public ClickHouseInt256Constant(BigInteger value) {
        super(value);
    }

    @Override
    public ClickHouseDataType getDataType() {
        return ClickHouseDataType.Int256;
    }
}
