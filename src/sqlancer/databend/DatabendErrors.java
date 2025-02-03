package sqlancer.databend;

import java.util.ArrayList;
import java.util.List;

import sqlancer.common.query.ExpectedErrors;

public final class DatabendErrors {

    private DatabendErrors() {
    }

    public static List<String> getExpressionErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("Division by zero");
        errors.add("divided by zero");
        errors.add("/ by zero");
        errors.add("ORDER BY position");
        errors.add("GROUP BY position");
        errors.add("no overload satisfies `not(Float64 NULL)`"); // TODO databend不允许出现not(float)，而a/b为float
        errors.add("no overload satisfies `not(Float64)`");
        errors.add("number overflowed while evaluating function"); // 表达式数值溢出
        errors.add("Unable to get field named");
        errors.add("no overload satisfies `and_filters");
        if (DatabendBugs.bug9162) {
            errors.add("downcast column error");
        }
        if (DatabendBugs.bug9018) {
            errors.add("index out of bounds");
        }
        if (DatabendBugs.bug9163) {
            errors.add("validity must be equal to the array's length");
        }
        if (DatabendBugs.bug9224) {
            errors.add("Can't cast column from nullable data into non-nullable type");
        }
        if (DatabendBugs.bug9234) {
            errors.add("called `Option::unwrap()` on a `None` value");
        }
        if (DatabendBugs.bug9264) {
            errors.add("assertion failed: offset + length <= self.length");
        }
        if (DatabendBugs.bug9806) {
            errors.add("segment pruning failure");
        }
        if (DatabendBugs.bug15568) {
            errors.add("Decimal overflow at line : 723 while evaluating function `to_decimal");
        }

        /*
         * TODO column为not null 时，注意default不能为null DROP DATABASE IF EXISTS databend2; CREATE DATABASE databend2; USE
         * databend2; CREATE TABLE t0(c0VARCHAR VARCHAR NULL, c1VARCHAR VARCHAR NULL, c2FLOAT FLOAT NOT NULL
         * DEFAULT(NULL)); CREATE TABLE t1(c0INT BIGINT NULL); INSERT INTO t0(c1varchar, c0varchar) VALUES
         * ('067596','19'), ('', '87');
         */
        errors.add("Can't cast column from null into non-nullable type");

        return errors;
    }

    public static void addExpressionErrors(ExpectedErrors errors) {
        errors.addAll(getExpressionErrors());
    }

    public static List<String> getInsertErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("Division by zero");
        errors.add("/ by zero");
        errors.add("violates not-null constraint");
        errors.add("number overflowed while evaluating function `"); // 不能在int16类型column上插入int64的数据

        return errors;
    }

    public static void addInsertErrors(ExpectedErrors errors) {
        errors.addAll(getInsertErrors());
    }

    public static List<String> getGroupByErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("Division by zero");
        errors.add("/ by zero");
        errors.add("Can't cast column from null into non-nullable type");
        errors.add("GROUP BY position");
        errors.add("GROUP BY items can't contain aggregate functions or window functions");

        return errors;
    }

    public static void addGroupByErrors(ExpectedErrors errors) {
        errors.addAll(getGroupByErrors());
    }

}
