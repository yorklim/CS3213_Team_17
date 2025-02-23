package sqlancer.doris.oracle.tlp;

import java.util.HashMap;
import java.util.Map;

import sqlancer.doris.ast.DorisExpression;
import sqlancer.doris.ast.DorisSelect;
import sqlancer.doris.visitor.DorisToStringVisitor;

public final class DorisQueryPartitioningTesterCommon {

    private DorisQueryPartitioningTesterCommon() {
    }

    public static Map<String, String> checkCommon(DorisSelect select, DorisExpression predicate,
            DorisExpression negatedPredicate, DorisExpression isNullPredicate) {
        select.setWhereClause(null);
        Map<String, String> map = new HashMap<>();
        map.put("originalQueryString", DorisToStringVisitor.asString(select));
        select.setWhereClause(predicate);
        map.put("firstQueryString", DorisToStringVisitor.asString(select));
        select.setWhereClause(negatedPredicate);
        map.put("secondQueryString", DorisToStringVisitor.asString(select));
        select.setWhereClause(isNullPredicate);
        map.put("thirdQueryString", DorisToStringVisitor.asString(select));
        return map;
    }

}
