package sqlancer.common.ast;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.regex.Pattern;

public class AstUtils {

    private static final double MAX_INT_FOR_WHICH_CONVERSION_TO_INT_IS_TRIED = Math.pow(2, 51 - 1) - 1;
    private static final double MIN_INT_FOR_WHICH_CONVERSION_TO_INT_IS_TRIED = -Math.pow(2, 51 - 1);

    private static final byte FILE_SEPARATOR = 0x1c;
    private static final byte GROUP_SEPARATOR = 0x1d;
    private static final byte RECORD_SEPARATOR = 0x1e;
    private static final byte UNIT_SEPARATOR = 0x1f;
    private static final byte SYNCHRONOUS_IDLE = 0x16;

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

    public static <T> T convertInternalHelper(String asString, boolean convertRealToInt, boolean convertIntToReal,
            Function<Long, T> func1, Function<Double, T> func2) {
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

    public static boolean startsWithWhitespace(String asString) {
        if (asString.isEmpty()) {
            return false;
        }
        char c = asString.charAt(0);
        switch (c) {
        case ' ':
        case '\t':
        case 0x0b:
        case '\f':
        case '\n':
        case '\r':
            return true;
        default:
            return false;
        }
    }

    public static boolean unprintAbleCharThatLetsBecomeNumberZero(String s) {
        // non-printable characters are ignored by Double.valueOf
        for (int i = 0; i < s.length(); i++) {
            char charAt = s.charAt(i);
            if (!Character.isISOControl(charAt) && !Character.isWhitespace(charAt)) {
                return false;
            }
            switch (charAt) {
            case GROUP_SEPARATOR:
            case FILE_SEPARATOR:
            case RECORD_SEPARATOR:
            case UNIT_SEPARATOR:
            case SYNCHRONOUS_IDLE:
                return true;
            default:
                // fall through
            }

            if (Character.isWhitespace(charAt)) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }
}
