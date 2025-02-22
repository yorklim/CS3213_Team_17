package sqlancer.transformations;

import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import net.sf.jsqlparser.util.deparser.SelectDeParser;

public abstract class AbstractRemoveFromSelectTransformation extends JSQLParserBasedTransformation {

    private SelectDeParser remover;

    protected abstract SelectDeParser createRemover();

    protected AbstractRemoveFromSelectTransformation(String description) {
        super(description);
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
        }
    }

}
