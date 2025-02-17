package sqlancer.common.ast;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.regex.Pattern;

import sqlancer.clickhouse.ast.constant.ClickHouseCreateConstant;
import sqlancer.common.ast.newast.Expression;

public class AstUtils {

    private static final double MAX_INT_FOR_WHICH_CONVERSION_TO_INT_IS_TRIED = Math.pow(2, 51 - 1) - 1;
    private static final double MIN_INT_FOR_WHICH_CONVERSION_TO_INT_IS_TRIED = -Math.pow(2, 51 - 1);
    
    public static Long castToIntStringHelper(String asString) {
        for (int i = asString.length(); i >= 0; i--) {
            try {
                String substring = asString.substring(0, i);
                Pattern p = Pattern.compile("[+-]?\\d\\d*");
                if (p.matcher(substring).matches()) {
                    BigDecimal bg = new BigDecimal(substring);
                    long result;
                    try {
                        result = bg.longValueExact();
                    } catch (ArithmeticException e) {
                        if (substring.startsWith("-")) {
                            result = Long.MIN_VALUE;
                        } else {
                            result = Long.MAX_VALUE;
                        }
                    }
                    return result;
                }
            } catch (Exception e) {

            }
        }
        return null;
    }

    public static <T> T convertInternal(String asString, boolean convertRealToInt, boolean convertIntToReal, Function<Long, T> func1, Function<Double, T> func2) {
        for (int i = asString.length(); i >= 0; i--) {
            try {
                String substring = asString.substring(0, i);
                double d = Double.parseDouble(substring);
                BigDecimal first = new BigDecimal(substring);
                long longValue = first.longValue();
                BigDecimal second = BigDecimal.valueOf(longValue);
                boolean isWithinConvertibleRange = longValue >= MIN_INT_FOR_WHICH_CONVERSION_TO_INT_IS_TRIED
                        && longValue <= MAX_INT_FOR_WHICH_CONVERSION_TO_INT_IS_TRIED && convertRealToInt;
                boolean isFloatingPointNumber = substring.contains(".") || substring.toUpperCase().contains("E");
                boolean doubleShouldBeConvertedToInt = isFloatingPointNumber && first.compareTo(second) == 0
                        && isWithinConvertibleRange;
                boolean isInteger = !isFloatingPointNumber && first.compareTo(second) == 0;
                if (doubleShouldBeConvertedToInt || isInteger && !convertIntToReal) {
                    // see https://www.sqlite.org/src/tktview/afdc5a29dc
                    return func1.apply(first.longValue());
                } else {
                    return func2.apply(d);
                }
            } catch (Exception e) {
            
            }
        }
        return null;
    }
}
