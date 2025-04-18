package sqlancer.hsqldb.ast;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import sqlancer.common.constant.DoubleConstantUtil;

public class HSQLDBConstant implements HSQLDBExpression {

    private HSQLDBConstant() {
    }

    public static class HSQLDBNullConstant extends HSQLDBConstant {

        @Override
        public String toString() {
            return "Null";
        }

    }

    public static class HSQLDBIntConstant extends HSQLDBConstant {

        private final int value;

        public HSQLDBIntConstant(long value) {
            this.value = (int) value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public long getValue() {
            return value;
        }

    }

    public static class HSQLDBDoubleConstant extends HSQLDBConstant {

        private final double value;

        public HSQLDBDoubleConstant(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public String toString() {
            return DoubleConstantUtil.doubleToString(value, DoubleConstantUtil.HSQLDB_POSITIVE_INFINITY,
                    DoubleConstantUtil.HSQLDB_NEGATIVE_INFINITY);
        }

    }

    public static class HSQLDBTextConstant extends HSQLDBConstant {

        private final String value;

        public HSQLDBTextConstant(String value) {
            this.value = value;
        }

        public HSQLDBTextConstant(String value, int size) {
            this.value = value.substring(0, Math.min(value.length(), size));
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "'" + value.replace("'", "''") + "'";
        }

    }

    public static class HSQLDBBinaryConstant extends HSQLDBConstant {

        private final String value;

        public HSQLDBBinaryConstant(long value, int size) {
            StringBuilder hex = new StringBuilder(Long.toHexString(value));
            if (hex.length() < 2) {
                hex.append('0');
            }
            this.value = hex.substring(0, Math.min(hex.length(), size));
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "X'" + value + "'";
        }

    }

    public static class HSQLDBDateConstant extends HSQLDBConstant {

        public String textRepr;

        public HSQLDBDateConstant(long val) {
            Timestamp timestamp = new Timestamp(val);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            textRepr = dateFormat.format(timestamp);
        }

        public String getValue() {
            return textRepr;
        }

        @Override
        public String toString() {
            return String.format("DATE '%s'", textRepr);
        }

    }

    public static class HSQLDBTimestampConstant extends HSQLDBConstant {

        public String textRepr;

        public HSQLDBTimestampConstant(long val) {
            Timestamp timestamp = new Timestamp(val);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            textRepr = dateFormat.format(timestamp);
        }

        public String getValue() {
            return textRepr;
        }

        @Override
        public String toString() {
            return String.format("TIMESTAMP '%s'", textRepr);
        }

    }

    public static class HSQLDBTimeConstant extends HSQLDBConstant {

        public String textRepr;

        public HSQLDBTimeConstant(long val) {
            Timestamp timestamp = new Timestamp(val);
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            textRepr = dateFormat.format(timestamp);
        }

        public String getValue() {
            return textRepr;
        }

        @Override
        public String toString() {
            return String.format("TIME '%s'", textRepr);
        }

    }

    public static class HSQLDBBooleanConstant extends HSQLDBConstant {

        private final boolean value;

        public HSQLDBBooleanConstant(boolean value) {
            this.value = value;
        }

        public boolean getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

    }

    public static HSQLDBExpression createStringConstant(String text, int size) {
        return new HSQLDBTextConstant(text, size);
    }

    public static HSQLDBExpression createFloatConstant(double val) {
        return new HSQLDBDoubleConstant(val);
    }

    public static HSQLDBExpression createIntConstant(long val) {
        return new HSQLDBIntConstant(val);
    }

    public static HSQLDBExpression createNullConstant() {
        return new HSQLDBNullConstant();
    }

    public static HSQLDBExpression createBooleanConstant(boolean val) {
        return new HSQLDBBooleanConstant(val);
    }

    public static HSQLDBExpression createDateConstant(long integer) {
        return new HSQLDBDateConstant(integer);
    }

    public static HSQLDBExpression createTimeConstant(long integer) {
        return new HSQLDBTimeConstant(integer);
    }

    public static HSQLDBExpression createTimestampConstant(long integer) {
        return new HSQLDBTimestampConstant(integer);
    }

    public static HSQLDBExpression createBinaryConstant(long nonCachedInteger, int size) {
        return new HSQLDBBinaryConstant(nonCachedInteger, size);
    }

}
