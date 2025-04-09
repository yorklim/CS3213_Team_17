package sqlancer.clickhouse.ast.constant;

import com.clickhouse.client.ClickHouseDataType;

public class ClickHouseInt16Constant extends ClickHouseLongConstant {

    public ClickHouseInt16Constant(long value) {
        super(value);
    }

    @Override
    public ClickHouseDataType getDataType() {
        return ClickHouseDataType.Int16;
    }
}
