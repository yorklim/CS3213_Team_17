package sqlancer.materialize.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import sqlancer.IgnoreMeException;
import sqlancer.Randomly;
import sqlancer.common.DBMSCommon;
import sqlancer.common.query.ExpectedErrors;
import sqlancer.materialize.MaterializeGlobalState;
import sqlancer.materialize.MaterializeProvider;
import sqlancer.materialize.MaterializeSchema;
import sqlancer.materialize.MaterializeSchema.MaterializeColumn;
import sqlancer.materialize.MaterializeSchema.MaterializeDataType;
import sqlancer.materialize.MaterializeSchema.MaterializeTable;
import sqlancer.materialize.MaterializeSchema.MaterializeTables;
import sqlancer.materialize.MaterializeVisitor;
import sqlancer.materialize.ast.MaterializeExpression;
import sqlancer.materialize.ast.MaterializeJoin;
import sqlancer.materialize.ast.MaterializeJoin.MaterializeJoinType;
import sqlancer.materialize.ast.MaterializeSelect.MaterializeFromTable;
import sqlancer.materialize.ast.MaterializeSelect.MaterializeSubquery;
import sqlancer.materialize.oracle.tlp.MaterializeTLPBase;

public final class MaterializeCommon {

    private MaterializeCommon() {
    }

    public static List<String> getCommonFetchErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("FULL JOIN is only supported with merge-joinable or hash-joinable join conditions");
        errors.add("but it cannot be referenced from this part of the query");
        errors.add("missing FROM-clause entry for table");

        errors.add("canceling statement due to statement timeout");

        errors.add("non-integer constant in GROUP BY");
        errors.add("must appear in the GROUP BY clause or be used in an aggregate function");
        errors.add("GROUP BY position");
        errors.add("result exceeds max size of");

        errors.add("does not exist");
        errors.add("aggregate functions are not allowed in");
        errors.add("is only defined for finite arguments");

        return errors;
    }

    public static void addCommonFetchErrors(ExpectedErrors errors) {
        errors.addAll(getCommonFetchErrors());
    }

    public static List<String> getCommonTableErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("is not commutative"); // exclude
        errors.add("operator requires run-time type coercion"); // exclude

        return errors;
    }

    public static void addCommonTableErrors(ExpectedErrors errors) {
        errors.addAll(getCommonTableErrors());
    }

    public static List<String> getCommonExpressionErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("You might need to add explicit type casts");
        errors.add("invalid regular expression");
        errors.add("could not determine which collation to use");
        errors.add("invalid regular expression");
        errors.add("operator does not exist");
        errors.add("quantifier operand invalid");
        errors.add("collation mismatch");
        errors.add("collations are not supported");
        errors.add("operator is not unique");
        errors.add("is not a valid binary digit");
        errors.add("invalid hexadecimal digit");
        errors.add("invalid hexadecimal data: odd number of digits");
        errors.add("zero raised to a negative power is undefined");
        errors.add("cannot convert infinity to numeric");
        errors.add("division by zero");
        errors.add("invalid input syntax for type money");
        errors.add("invalid input syntax for type");
        errors.add("cannot cast type");
        errors.add("value overflows numeric format");
        errors.add("numeric field overflow");
        errors.add("LIKE pattern must not end with escape character");
        errors.add("is of type boolean but expression is of type text");
        errors.add("a negative number raised to a non-integer power yields a complex result");
        errors.add("could not determine polymorphic type because input has type unknown");
        errors.add("character number must be positive");
        errors.add("unterminated escape sequence");
        errors.add("cannot be matched");
        errors.add("clause must have type"); // "not" in having doesn't work
        errors.add("argument must have type"); // "not" in having doesn't work
        errors.add("CAST does not support casting from");
        errors.add("aggregate functions are not allowed in");
        errors.add("only defined for finite arguments");
        errors.add("unable to parse column reference in GROUP BY clause"); // TODO
        errors.addAll(getToCharFunctionErrors());
        errors.addAll(getBitStringOperationErrors());
        errors.addAll(getFunctionErrors());
        errors.addAll(getCommonRangeExpressionErrors());
        errors.addAll(getCommonRegexExpressionErrors());

        return errors;
    }

    public static void addCommonExpressionErrors(ExpectedErrors errors) {
        errors.addAll(getCommonExpressionErrors());
    }

    private static List<String> getToCharFunctionErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("multiple decimal points");
        errors.add("and decimal point together");
        errors.add("multiple decimal points");
        errors.add("cannot use \"S\" twice");
        errors.add("must be ahead of \"PR\"");
        errors.add("cannot use \"S\" and \"PL\"/\"MI\"/\"SG\"/\"PR\" together");
        errors.add("cannot use \"S\" and \"SG\" together");
        errors.add("cannot use \"S\" and \"MI\" together");
        errors.add("cannot use \"S\" and \"PL\" together");
        errors.add("cannot use \"PR\" and \"S\"/\"PL\"/\"MI\"/\"SG\" together");
        errors.add("is not a number");

        return errors;
    }

    private static List<String> getBitStringOperationErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("cannot XOR bit strings of different sizes");
        errors.add("cannot AND bit strings of different sizes");
        errors.add("cannot OR bit strings of different sizes");
        errors.add("must be type boolean, not type text");

        return errors;
    }

    private static List<String> getFunctionErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("out of valid range"); // get_bit/get_byte
        errors.add("cannot take logarithm of a negative number");
        errors.add("cannot take logarithm of zero");
        errors.add("requested character too large for encoding"); // chr
        errors.add("null character not permitted"); // chr
        errors.add("requested character not valid for encoding"); // chr
        errors.add("requested length too large"); // repeat
        errors.add("invalid memory alloc request size"); // repeat
        errors.add("encoding conversion from UTF8 to ASCII not supported"); // to_ascii
        errors.add("negative substring length not allowed"); // substr
        errors.add("invalid mask length"); // set_masklen

        return errors;
    }

    private static List<String> getCommonRegexExpressionErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("is not a valid hexadecimal digit");

        return errors;
    }

    public static List<String> getCommonRangeExpressionErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("range lower bound must be less than or equal to range upper bound");
        errors.add("result of range difference would not be contiguous");
        errors.add("out of range");
        errors.add("malformed range literal");
        errors.add("result of range union would not be contiguous");

        return errors;
    }

    public static void addCommonRangeExpressionErrors(ExpectedErrors errors) {
        errors.addAll(getCommonExpressionErrors());
    }

    public static List<String> getCommonInsertUpdateErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("value too long for type character");
        errors.add("not found in view targetlist");
        errors.add("CAST does not support casting from");

        return errors;
    }

    public static void addCommonInsertUpdateErrors(ExpectedErrors errors) {
        errors.addAll(getCommonExpressionErrors());
    }

    public static List<String> getGroupingErrors() {
        ArrayList<String> errors = new ArrayList<>();

        errors.add("non-integer constant in GROUP BY"); // TODO
        errors.add("unable to parse column reference in GROUP BY clause"); // TODO
        errors.add("must appear in the GROUP BY clause or be used in an aggregate function");
        errors.add("is not in select list");
        errors.add("aggregate functions are not allowed in");

        return errors;
    }

    public static void addGroupingErrors(ExpectedErrors errors) {
        errors.addAll(getGroupingErrors());
    }

    public static boolean appendDataType(MaterializeDataType type, StringBuilder sb) throws AssertionError {
        boolean serial = false;
        switch (type) {
        case BOOLEAN:
            sb.append("boolean");
            break;
        case INT:
            sb.append(Randomly.fromOptions("smallint", "integer", "bigint"));
            break;
        case TEXT:
            if (Randomly.getBoolean()) {
                sb.append("TEXT");
            } else {
                if (MaterializeProvider.generateOnlyKnown || Randomly.getBoolean()) {
                    sb.append("VAR");
                }
                sb.append("CHAR");
                sb.append("(");
                sb.append(ThreadLocalRandom.current().nextInt(1, 500));
                sb.append(")");
            }
            break;
        case DECIMAL:
            sb.append("DECIMAL");
            break;
        case FLOAT:
            sb.append("REAL");
            break;
        case REAL:
            sb.append("FLOAT");
            break;
        case BIT:
            sb.append("INT");
            break;
        default:
            throw new AssertionError(type);
        }
        return serial;
    }

    public enum TableConstraints {
        CHECK, PRIMARY_KEY, FOREIGN_KEY, EXCLUDE
    }

    public static void addTableConstraints(boolean excludePrimaryKey, StringBuilder sb, MaterializeTable table,
            MaterializeGlobalState globalState, ExpectedErrors errors) {
        // TODO constraint name
        List<TableConstraints> tableConstraints = Randomly.nonEmptySubset(TableConstraints.values());
        if (excludePrimaryKey) {
            tableConstraints.remove(TableConstraints.PRIMARY_KEY);
        }
        if (globalState.getSchema().getDatabaseTables().isEmpty()) {
            tableConstraints.remove(TableConstraints.FOREIGN_KEY);
        }
        for (TableConstraints t : tableConstraints) {
            sb.append(", ");
            // TODO add index parameters
            addTableConstraint(sb, table, globalState, t, errors);
        }
    }

    public static void addTableConstraint(StringBuilder sb, MaterializeTable table, MaterializeGlobalState globalState,
            ExpectedErrors errors) {
        addTableConstraint(sb, table, globalState, Randomly.fromOptions(TableConstraints.values()), errors);
    }

    private static void addTableConstraint(StringBuilder sb, MaterializeTable table, MaterializeGlobalState globalState,
            TableConstraints t, ExpectedErrors errors) {
        List<MaterializeColumn> randomNonEmptyColumnSubset = table.getRandomNonEmptyColumnSubset();
        List<MaterializeColumn> otherColumns;
        MaterializeCommon.addCommonExpressionErrors(errors);
        switch (t) {
        case CHECK:
            sb.append("CHECK(");
            sb.append(MaterializeVisitor.getExpressionAsString(globalState, MaterializeDataType.BOOLEAN,
                    table.getColumns()));
            sb.append(")");
            errors.add("constraint must be added to child tables too");
            errors.add("missing FROM-clause entry for table");
            break;
        case PRIMARY_KEY:
            sb.append("PRIMARY KEY(");
            sb.append(randomNonEmptyColumnSubset.stream().map(c -> c.getName()).collect(Collectors.joining(", ")));
            sb.append(")");
            break;
        case FOREIGN_KEY:
            sb.append("FOREIGN KEY (");
            sb.append(randomNonEmptyColumnSubset.stream().map(c -> c.getName()).collect(Collectors.joining(", ")));
            sb.append(") REFERENCES ");
            MaterializeTable randomOtherTable = globalState.getSchema().getRandomTable(tab -> !tab.isView());
            sb.append(randomOtherTable.getName());
            if (randomOtherTable.getColumns().size() < randomNonEmptyColumnSubset.size()) {
                throw new IgnoreMeException();
            }
            otherColumns = randomOtherTable.getRandomNonEmptyColumnSubset(randomNonEmptyColumnSubset.size());
            sb.append("(");
            sb.append(otherColumns.stream().map(c -> c.getName()).collect(Collectors.joining(", ")));
            sb.append(")");
            if (Randomly.getBoolean()) {
                sb.append(" ");
                sb.append(Randomly.fromOptions("MATCH FULL", "MATCH SIMPLE"));
            }
            if (Randomly.getBoolean()) {
                sb.append(" ON DELETE ");
                errors.add("ERROR: invalid ON DELETE action for foreign key constraint containing generated column");
                deleteOrUpdateAction(sb);
            }
            if (Randomly.getBoolean()) {
                sb.append(" ON UPDATE ");
                errors.add("invalid ON UPDATE action for foreign key constraint containing generated column");
                deleteOrUpdateAction(sb);
            }
            if (Randomly.getBoolean()) {
                sb.append(" ");
                if (Randomly.getBoolean()) {
                    sb.append("DEFERRABLE");
                    if (Randomly.getBoolean()) {
                        sb.append(" ");
                        sb.append(Randomly.fromOptions("INITIALLY DEFERRED", "INITIALLY IMMEDIATE"));
                    }
                } else {
                    sb.append("NOT DEFERRABLE");
                }
            }
            break;
        case EXCLUDE:
            sb.append("EXCLUDE ");
            sb.append("(");
            // TODO [USING index_method ]
            for (int i = 0; i < Randomly.smallNumber() + 1; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                appendExcludeElement(sb, globalState, table.getColumns());
                sb.append(" WITH ");
                appendOperator(sb, globalState.getOperators());
            }
            sb.append(")");
            errors.add("is not valid");
            errors.add("no operator matches");
            errors.add("operator does not exist");
            errors.add("unknown has no default operator class");
            errors.add("exclusion constraints are not supported on partitioned tables");
            errors.add("The exclusion operator must be related to the index operator class for the constraint");
            errors.add("could not create exclusion constraint");
            // TODO: index parameters
            if (Randomly.getBoolean()) {
                sb.append(" WHERE ");
                sb.append("(");
                sb.append(MaterializeVisitor.asString(MaterializeExpressionGenerator.generateExpression(globalState,
                        table.getColumns(), MaterializeDataType.BOOLEAN)));
                sb.append(")");
            }
            break;
        default:
            throw new AssertionError(t);
        }
    }

    private static void appendOperator(StringBuilder sb, List<String> operators) {
        sb.append(Randomly.fromList(operators));
    }

    // complete
    private static void appendExcludeElement(StringBuilder sb, MaterializeGlobalState globalState,
            List<MaterializeColumn> columns) {
        if (Randomly.getBoolean()) {
            // append column name
            sb.append(Randomly.fromList(columns).getName());
        } else {
            // append expression
            sb.append("(");
            sb.append(MaterializeVisitor
                    .asString(MaterializeExpressionGenerator.generateExpression(globalState, columns)));
            sb.append(")");
        }
        if (Randomly.getBoolean()) {
            sb.append(" ");
            sb.append(Randomly.fromList(globalState.getOpClasses()));
        }
        if (Randomly.getBoolean()) {
            sb.append(" ");
            sb.append(Randomly.fromOptions("ASC", "DESC"));
        }
        if (Randomly.getBoolean()) {
            sb.append(" NULLS ");
            sb.append(Randomly.fromOptions("FIRST", "LAST"));
        }
    }

    private static void deleteOrUpdateAction(StringBuilder sb) {
        sb.append(Randomly.fromOptions("NO ACTION", "RESTRICT", "CASCADE", "SET NULL", "SET DEFAULT"));
    }

    public static String getFreeIndexName(MaterializeSchema s) {
        List<String> indexNames = s.getIndexNames();
        String candidateName;
        do {
            candidateName = DBMSCommon.createIndexName((int) Randomly.getNotCachedInteger(0, 100));
        } while (indexNames.contains(candidateName));
        return candidateName;
    }

    public static List<MaterializeJoin> getCommonJoinStatements(MaterializeGlobalState globalState,
            List<MaterializeColumn> columns, List<MaterializeTable> tables) {
        List<MaterializeJoin> joinStatements = new ArrayList<>();
        MaterializeExpressionGenerator gen = new MaterializeExpressionGenerator(globalState).setColumns(columns);
        for (int i = 1; i < tables.size(); i++) {
            MaterializeExpression joinClause = gen.generateExpression(MaterializeDataType.BOOLEAN);
            MaterializeTable table = Randomly.fromList(tables);
            tables.remove(table);
            MaterializeJoinType options = MaterializeJoinType.getRandom();
            MaterializeJoin j = new MaterializeJoin(new MaterializeFromTable(table, Randomly.getBoolean()), joinClause,
                    options);
            joinStatements.add(j);
        }
        // JOIN subqueries
        for (int i = 0; i < Randomly.smallNumber(); i++) {
            MaterializeTables subqueryTables = globalState.getSchema().getRandomTableNonEmptyTables();
            MaterializeSubquery subquery = MaterializeTLPBase.createSubquery(globalState, String.format("sub%d", i),
                    subqueryTables);
            MaterializeExpression joinClause = gen.generateExpression(MaterializeDataType.BOOLEAN);
            MaterializeJoinType options = MaterializeJoinType.getRandom();
            MaterializeJoin j = new MaterializeJoin(subquery, joinClause, options);
            joinStatements.add(j);
        }
        return joinStatements;
    }
}
