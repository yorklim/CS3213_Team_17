package sqlancer.transformations;

import java.util.List;

import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.util.deparser.SelectDeParser;

/**
 * remove columns of a select: e.g. select a, b, c from t -> select a from t.
 */
public class RemoveColumnsOfSelect extends AbstractRemoveFromSelectTransformation {

    @Override
    protected SelectDeParser createRemover() {
        return new SelectDeParser() {
            @Override
            public void visit(PlainSelect plainSelect) {
                tryRemoveElms(plainSelect, plainSelect.getSelectItems(), PlainSelect::setSelectItems);
                super.visit(plainSelect);
            }
        };
    }

    public RemoveColumnsOfSelect() {
        super("remove columns of a select");
    }

    @Override
    protected void handleWithItems(Select select) {
        List<WithItem> withItemsList = select.getWithItemsList();
        if (withItemsList == null) {
            return;
        }

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
}
