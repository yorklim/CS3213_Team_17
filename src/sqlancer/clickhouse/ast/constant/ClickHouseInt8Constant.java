package sqlancer.clickhouse.ast.constant;

import com.clickhouse.client.ClickHouseDataType;

public class ClickHouseInt8Constant extends ClickHouseIntConstant {

    public ClickHouseInt8Constant(int value) {
        super(value);
    }

    @Override
    public ClickHouseDataType getDataType() {
        return ClickHouseDataType.Int8;
    }
}
