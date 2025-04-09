package sqlancer.clickhouse.ast.constant;

import com.clickhouse.client.ClickHouseDataType;

public class ClickHouseUInt16Constant extends ClickHouseLongConstant {

    public ClickHouseUInt16Constant(long value) {
        super(value);
    }

    @Override
    public ClickHouseDataType getDataType() {
        return ClickHouseDataType.UInt16;
    }
}
