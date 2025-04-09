package sqlancer.common.constant;

public final class DoubleConstantUtil {

    public static final String TIDB_POSITIVE_INFINITY = "'+Inf'";
    public static final String TIDB_NEGATIVE_INFINITY = "'-Inf'";

    public static final String DUCKDB_POSITIVE_INFINITY = "'+Inf'";
    public static final String DUCKDB_NEGATIVE_INFINITY = "'-Inf'";

    public static final String HSQLDB_POSITIVE_INFINITY = "1.0e1/0.0e1";
    public static final String HSQLDB_NEGATIVE_INFINITY = "-1.0e1/0.0e1";

    public static final String YCQL_POSITIVE_INFINITY = "'+Inf'";
    public static final String YCQL_NEGATIVE_INFINITY = "'-Inf'";

    public static final String COCKROACHDB_POSITIVE_INFINITY = "FLOAT '+Inf'";
    public static final String COCKROACHDB_NEGATIVE_INFINITY = "FLOAT '-Inf'";

    public static final String PRESTO_POSITIVE_INFINITY = "infinity()";
    public static final String PRESTO_NEGATIVE_INFINITY = "-infinity()";

    private DoubleConstantUtil() {
    }

    public static String doubleToString(double value, String posInf, String negInf) {
        if (value == Double.POSITIVE_INFINITY) {
            return posInf;
        } else if (value == Double.NEGATIVE_INFINITY) {
            return negInf;
        }
        return String.valueOf(value);
    }
}
