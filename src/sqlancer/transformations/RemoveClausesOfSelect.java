package sqlancer.transformations;

import java.util.List;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.Distinct;
import net.sf.jsqlparser.statement.select.GroupByElement;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.Offset;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.util.deparser.SelectDeParser;

/**
 * remove clauses of a select, such as join, where, group by, distinct, offset, limit.
 *
 * e.g. select * from t where a = b offset 1 limit 1 -> select * from t;
 */

public class RemoveClausesOfSelect extends AbstractRemoveFromSelectTransformation {

    public RemoveClausesOfSelect() {
        super("remove clauses of select");
    }

    @Override
    protected SelectDeParser createRemover() {
        return new SelectDeParser() {
            @Override
            public void visit(PlainSelect plainSelect) {
                handleSelect(plainSelect);
                super.visit(plainSelect);
            }
        };
    }

    @Override
    protected void handleWithItems(Select select) {
        List<WithItem> withItemsList = select.getWithItemsList();
        if (withItemsList == null) {
            return;
        }

        tryRemoveElms(select, withItemsList, Select::setWithItemsList);

        for (WithItem withItem : withItemsList) {
            SubSelect subSelect = withItem.getSubSelect();
            if (subSelect == null) {
                return;
            }

            if (subSelect.getSelectBody() != null) {
                subSelect.getSelectBody().accept(remover);
            }
        }
    }

    private void handleSelect(PlainSelect plainSelect) {

        Expression where = plainSelect.getWhere();
        if (where != null) {
            tryRemove(plainSelect, where, PlainSelect::setWhere);
        }

        GroupByElement groupByElement = plainSelect.getGroupBy();
        if (groupByElement != null) {
            tryRemove(plainSelect, groupByElement, PlainSelect::setGroupByElement);
        }

        Distinct distinct = plainSelect.getDistinct();
        if (distinct != null) {
            tryRemove(plainSelect, distinct, PlainSelect::setDistinct);
        }

        Offset offset = plainSelect.getOffset();
        if (offset != null) {
            tryRemove(plainSelect, offset, PlainSelect::setOffset);
        }

        Limit limit = plainSelect.getLimit();
        if (offset != null) {
            tryRemove(plainSelect, limit, PlainSelect::setLimit);
        }
    }

}
