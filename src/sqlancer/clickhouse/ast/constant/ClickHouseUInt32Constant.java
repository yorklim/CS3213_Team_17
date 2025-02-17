package sqlancer.clickhouse.ast.constant;

import com.clickhouse.client.ClickHouseDataType;

public class ClickHouseUInt32Constant extends ClickHouseLongConstant {

    public ClickHouseUInt32Constant(long value) {
        super(value);
    }

    @Override
    public ClickHouseDataType getDataType() {
        return ClickHouseDataType.UInt32;
    }
}
