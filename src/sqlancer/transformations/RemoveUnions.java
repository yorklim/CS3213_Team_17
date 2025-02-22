package sqlancer.transformations;

import java.util.List;

import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.util.deparser.SelectDeParser;

/**
 * try removing sub selects of a union statement.
 *
 * e.g. select 1 union select 2 -> select 1
 */

public class RemoveUnions extends AbstractRemoveFromSelectTransformation {

    public RemoveUnions() {
        super("remove union selects");
    }

    @Override
    protected SelectDeParser createRemover() {
        return new SelectDeParser() {
            @Override
            public void visit(SetOperationList list) {
                List<SelectBody> selectBodyList = list.getSelects();
                tryRemoveElms(list, selectBodyList, SetOperationList::setSelects);
                super.visit(list);
            }
        };
    }

    @Override
    protected void handleWithItems(Select select) {}

}
