package sqlancer.transformations;

import java.util.List;

import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import net.sf.jsqlparser.util.deparser.SelectDeParser;

public abstract class AbstractRemoveFromSelectTransformation extends JSQLParserBasedTransformation {

    protected SelectDeParser remover;

    protected abstract SelectDeParser createRemover();

    protected AbstractRemoveFromSelectTransformation(String description) {
        super(description);
    }

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

    @Override
    public boolean init(String sql) {
        boolean baseSuc = super.init(sql);
        if (!baseSuc) {
            return false;
        }

        this.remover = createRemover();
        this.remover.setExpressionVisitor(new ExpressionDeParser(remover, new StringBuilder()));
        return true;
    }

    @Override
    public void apply() {
        super.apply();
        if (statement instanceof Select) {
            Select select = (Select) statement;
            select.getSelectBody().accept(remover);
            handleWithItems(select);
        }
    }

}
