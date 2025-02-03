package sqlancer.presto;

import java.util.ArrayList;
import java.util.List;

import sqlancer.common.query.ExpectedErrors;

public final class PrestoErrors {

    private PrestoErrors() {
    }

    public static List<String> getExpressionErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.addAll(getFunctionErrors());

        // Presto errors
        errors.add("cannot be applied to");
        errors.add("LIKE expression must evaluate to a varchar");
        errors.add("JOIN ON clause must evaluate to a boolean");
        // errors.add("Unexpected parameters");

        // SELECT SUM(count) FROM (SELECT
        // CAST((-179769313486231570000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000.0000
        // IS NOT NULL AND
        // -179769313486231570000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000.0000)
        // AS BIGINT)as count FROM t0) as res
        errors.add("Decimal overflow");
        errors.add("long overflow");
        errors.add("multiplication overflow");
        errors.add("addition overflow");
        errors.add("subtraction overflow");

        // cast
        // errors.add("Cannot cast");
        errors.add("Value cannot be cast to");
        errors.add("Cannot cast DECIMAL");
        errors.add("Cannot cast BIGINT");
        errors.add("Cannot cast INTEGER");

        // TODO: check
        errors.add("io.airlift.slice.Slice cannot be cast to java.lang.Number");
        errors.add("class io.airlift.slice.Slice cannot be cast to class java.lang.Number");
        if (PrestoBugs.bug23324) {
            errors.add("Cannot cast java.lang.Long to io.airlift.slice.Slice");
        }
        errors.add("Cannot cast java.lang.String to java.util.List");
        errors.add("Unexpected subquery expression in logical plan");

        // 9223372036854775808
        errors.add("Invalid numeric literal");

        errors.add("Division by zero");
        errors.add("/ by zero");

        errors.add("Cannot subtract hour, minutes or seconds from a date");
        errors.add("Cannot add hour, minutes or seconds to a date");

        errors.add("DECIMAL scale must be in range");
        errors.add("IN value and list items must be the same type");
        errors.add("is not a valid timestamp literal");
        errors.add("Unknown time-zone ID");
        errors.add("GROUP BY position");

        // ARRAY
        errors.add("Unknown type: ARRAY");

        // SELECT
        errors.add("WHERE clause must evaluate to a boolean");
        errors.add("HAVING clause must evaluate to a boolean");
        errors.add("not yet implemented");

        errors.add("Value expression and result of subquery must be of the same type for quantified comparison");
        errors.add("All IN list values must be the same type");
        errors.add("All CASE results must be the same type");
        errors.add("Mismatched types");
        errors.add("CASE operand type does not match WHEN clause operand type");
        errors.add("Subquery result type must be orderable");
        errors.add("Escape character must be followed by '%', '_' or the escape character itself");
        errors.add("Types are not comparable with NULLIF");
        errors.add("not of the same type");

        if (PrestoBugs.bug23613) {
            errors.add("at index 1");
        }

        return errors;
    }

    public static void addExpressionErrors(ExpectedErrors errors) {
        errors.addAll(getExpressionErrors());
    }

    private static List<String> getRegexErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("missing ]");
        errors.add("missing )");
        errors.add("invalid escape sequence");
        errors.add("no argument for repetition operator: ");
        errors.add("bad repetition operator");
        errors.add("trailing \\");
        errors.add("invalid perl operator");
        errors.add("invalid character class range");
        errors.add("width is not integer");

        return errors;
    }

    private static List<String> getFunctionErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("SUBSTRING cannot handle negative lengths");
        errors.add("is undefined outside [-1,1]"); // ACOS etc
        errors.add("invalid type specifier"); // PRINTF
        errors.add("argument index out of range"); // PRINTF
        errors.add("invalid format string"); // PRINTF
        errors.add("number is too big"); // PRINTF
        errors.add("Like pattern must not end with escape character!"); // LIKE
        errors.add("Could not choose a best candidate function for the function call \"date_part"); // date_part
        errors.add("extract specifier"); // date_part
        errors.add("not recognized"); // date_part
        errors.add("not supported"); // date_part
        errors.add("Failed to cast");
        errors.add("Conversion Error");
        errors.add("Could not cast value");
        errors.add("Insufficient padding in RPAD"); // RPAD
        errors.add("Could not choose a best candidate function for the function call"); // monthname
        errors.add("expected a numeric precision field"); // ROUND
        errors.add("with non-constant precision is not supported"); // ROUND
        errors.add("Unexpected parameters");
        errors.add("not registered");
        errors.add("Expected: least(E) E:orderable");
        errors.add("Expected: greatest(E) E:orderable");
        errors.add("Expected: max_by(V, K) K:orderable, V, max_by(V, K, bigint) V, K:orderable");
        errors.add("Expected: min_by(V, K) K:orderable, V, min_by(V, K, bigint) V, K:orderable");
        return errors;
    }

    // TODO: cover presto error
    public static List<String> getInsertErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.addAll(getRegexErrors());
        errors.addAll(getExpressionErrors());

        errors.add("NOT NULL constraint failed");
        errors.add("PRIMARY KEY or UNIQUE constraint violated");
        errors.add("duplicate key");
        errors.add("can't be cast because the value is out of range for the destination type");
        errors.add("Could not convert string");
        errors.add("Unimplemented type for cast");
        errors.add("field value out of range");
        errors.add("CHECK constraint failed");
        errors.add("Cannot explicitly insert values into rowid column"); // TODO: don't insert into rowid
        errors.add(" Column with name rowid does not exist!"); // currently, there doesn't seem to way to determine if
        // the table has a primary key
        errors.add("Could not cast value");
        errors.add("create unique index, table contains duplicate data");
        errors.add("Failed to cast");

        errors.add("Values rows have mismatched types");
        errors.add("Mismatch at column");
        errors.add("This connector does not support updates or deletes");
        errors.add("Values rows have mismatched types");
        errors.add("Invalid numeric literal");

        return errors;
    }

    public static void addInsertErrors(ExpectedErrors errors) {
        errors.addAll(getInsertErrors());
    }

    public static List<String> getGroupByErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("must be an aggregate expression or appear in GROUP BY clause");

        return errors;
    }

    public static void addGroupByErrors(ExpectedErrors errors) {
        errors.addAll(getGroupByErrors());
    }

}
