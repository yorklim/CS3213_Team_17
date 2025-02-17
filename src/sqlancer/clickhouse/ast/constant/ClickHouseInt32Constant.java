package sqlancer.clickhouse.ast.constant;

import com.clickhouse.client.ClickHouseDataType;

public class ClickHouseInt32Constant extends ClickHouseLongConstant {

    public ClickHouseInt32Constant(long value) {
        super(value);
    }

    @Override
    public ClickHouseDataType getDataType() {
        return ClickHouseDataType.Int32;
    }
}
